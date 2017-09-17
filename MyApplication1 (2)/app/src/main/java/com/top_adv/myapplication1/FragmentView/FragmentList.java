package com.top_adv.myapplication1.FragmentView;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.ParcelList;
import com.top_adv.myapplication1.ListView.ItemDetailPage;
import com.top_adv.myapplication1.ListView.ListData;
import com.top_adv.myapplication1.ListView.RecyclerViewAdapter;
import com.top_adv.myapplication1.Login.LoginActivity;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.MainViewPage;
import com.top_adv.myapplication1.NetWork.AsyncCallBack;
import com.top_adv.myapplication1.NetWork.JsonParsing;
import com.top_adv.myapplication1.NetWork.NetWorkConnectionImpl;
import com.top_adv.myapplication1.ProgressBarAsync;
import com.top_adv.myapplication1.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sst on 2016-10-14.
 */
public class FragmentList extends Fragment {
    private static String TAG = MainActivity.TAG;
    private static String ADVVO_TO_MAIN_VIEW_PAGE = "main_view_page.advVO";

    private RecyclerView fRecyclerView;
    private ParcelList pLists;
    private ArrayList<ListVO> lists;
    private Context fContext;

    private JsonParsing jsonParsing;

    private SwipeRefreshLayout frag_swipeRefresh;
    private RecyclerViewAdapter mAdapter;

    public ProgressDialog progressDialog;

    private SearchView listSearch;

    public FragmentList() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getIntentAdvVO();
        createProgressDialog();
        createListView();


    }

    public void getIntentAdvVO() {
        Intent intent = getActivity().getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            pLists = bundle.getParcelable(ADVVO_TO_MAIN_VIEW_PAGE);
            lists = pLists.getList_lists();
            System.out.println("lists data ex)"+lists.get(0).getAdv_title());
        }
    }

    public void createProgressDialog(){
        progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setContentView(R.layout.ring_progress);
        for(int i=0; i<lists.size(); i++){
            System.out.println("size : "+i);
            lists.get(i).setProgressDialog(progressDialog);
        }
    }

    public void createListView(){
        fRecyclerView = (RecyclerView)getActivity().findViewById(R.id.frag_recyclerView);
        frag_swipeRefresh = (SwipeRefreshLayout)getActivity().findViewById(R.id.frag_swipeRefresh);
        listSearch = (SearchView)getActivity().findViewById(R.id.text_list_search);
        listSearch.onActionViewExpanded();
        listSearch.clearFocus();

        // RecyclerView 사이즈 고정?
        //mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(lists);
        fRecyclerView.setAdapter(mAdapter);

        frag_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jsonParsing = new JsonParsing(ListVO.class);

                NetWorkConnectionImpl network = new NetWorkConnectionImpl(new AsyncCallBack() {
                    @Override
                    public void onTaskDone(Object... params) {
                        try {
                            if(params != null) {
                                lists = jsonParsing.getResult(params);

                                // Trade_status가 광고중인 제품만 보이기
                                /*
                                Iterator<ListVO> iter = lists.iterator();
                                while(iter.hasNext()) {
                                    ListVO vo = iter.next();
                                    if(!vo.getTrade_status().equals("광고중"))
                                        iter.remove();
                                }
                                */
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException ee) {
                            ee.printStackTrace();
                        }
                    }
                }, (AppCompatActivity) getActivity()) {};
                network.setMethod("android/getAdvList",false,false,"GET");
                network.getNetWorkResult();

                mAdapter.notifyDataSetChanged();

                frag_swipeRefresh.setRefreshing(false);
            }
        });

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView.OnQueryTextListener queryTextListener;

        if(listSearch != null) {
            listSearch.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            listSearch.setQueryHint("Search");
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mAdapter.setFilter(filter(lists, listSearch.getQuery().toString()));
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mAdapter.setFilter(filter(lists, listSearch.getQuery().toString()));
                    return true;
                }
            };
            listSearch.setOnQueryTextListener(queryTextListener);
        }
    }

    public ArrayList<ListVO> filter(ArrayList<ListVO> listData, String query) {
        query = query.toLowerCase();
        ArrayList<ListVO> filterList = new ArrayList<ListVO>();

        if(query != null) {
            for(int i=0; i<listData.size(); i++) {
                String title = listData.get(i).getAdv_title().toLowerCase();
                if(title.contains(query))
                    filterList.add(listData.get(i));
            }
        }

        return filterList;
    }

/*
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fRecyclerView.setAdapter(mAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }*/
}
