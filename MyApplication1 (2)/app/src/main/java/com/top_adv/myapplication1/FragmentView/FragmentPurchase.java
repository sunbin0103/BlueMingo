package com.top_adv.myapplication1.FragmentView;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.ParcelList;
import com.top_adv.myapplication1.ListView.UserListData;
import com.top_adv.myapplication1.ListView.UserRecylcerAdapter;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;

/**
 * Created by sst on 2016-10-20.
 */
public class FragmentPurchase extends Fragment {
    private static String TAG = MainActivity.TAG;
    private static String ADVVO_TO_MAIN_VIEW_PAGE = "main_view_page.advVO";

    private static RecyclerView fRecyclerView;
   //private static AdvVO fAdvVO;
   private ParcelList pLists;
    private static Context fContext;

    public FragmentPurchase() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_purchase, container, false);
        Log.i(TAG,"onCreateView()");

        final ArrayList<ListVO> lists = getIntentAdvVO();
        createListView(null, layout);

        TextView user_name = (TextView)getActivity().findViewById(R.id.user_name);
        user_name.setText("user");

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"onActivityCreated()");
    }

    public ArrayList<ListVO> getIntentAdvVO() {
        Intent intent = getActivity().getIntent();
        ArrayList<ListVO> lists = null;
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            pLists = bundle.getParcelable(ADVVO_TO_MAIN_VIEW_PAGE);
            //Log.i(TAG, "purchase. onCreateView AdvVO : " + fAdvVO.getAdv_title());
        }
        return lists;
    }

    public void createListView(AdvVO fAdvVO, View view){
        fContext = view.getContext();
        fRecyclerView = (RecyclerView)view.findViewById(R.id.purchase_recyclerView);
        //mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(fContext);
        fRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<UserListData> listData = new ArrayList<>();

        UserRecylcerAdapter mAdapter = new UserRecylcerAdapter(listData);
        fRecyclerView.setAdapter(mAdapter);

        // 여기서 해당 유저의 주문VO 불러와야 한다.
        // 불러와서 add 해주기.

        if(fAdvVO != null) {
            listData.add(new UserListData(fAdvVO.getAdv_key(),R.drawable.product1,fAdvVO.getAdv_title(),R.drawable.group_purchase,"2016/10/20"));
        }
        listData.add(new UserListData(1,R.drawable.product1,"title",R.drawable.direct_purchase,"2016/10/20"));
        listData.add(new UserListData(1,R.drawable.product2,"title",R.drawable.group_purchase,"2016/10/20"));
        listData.add(new UserListData(1,R.drawable.product3,"title",R.drawable.group_purchase,"2016/10/20"));
        listData.add(new UserListData(1,R.drawable.product1,"title",R.drawable.group_purchase,"2016/10/20"));
        listData.add(new UserListData(1,R.drawable.product2,"title",R.drawable.direct_purchase,"2016/10/20"));
        listData.add(new UserListData(1,R.drawable.product3,"title",R.drawable.group_purchase,"2016/10/20"));

    }


}
