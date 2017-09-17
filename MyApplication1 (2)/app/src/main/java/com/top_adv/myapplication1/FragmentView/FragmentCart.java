package com.top_adv.myapplication1.FragmentView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.ParcelList;
import com.top_adv.myapplication1.ListView.CartListData;
import com.top_adv.myapplication1.ListView.CartRecyclerAdapter;
import com.top_adv.myapplication1.ListView.ListData;
import com.top_adv.myapplication1.ListView.RecyclerViewAdapter;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sst on 2016-10-18.
 */
public class FragmentCart extends Fragment {
    private static String TAG = MainActivity.TAG;
    private static String ADVVO_TO_MAIN_VIEW_PAGE = "main_view_page.advVO";

    private RecyclerView fRecyclerView;
    private AdvVO fAdvVO;
    private ParcelList pLists;
    private ArrayList<ListVO> lists;
    private Context fContext;

    public FragmentCart() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
            userDB를 통하여 체크된 AdvVO를 선별적으로 가져와서 리스트로 뿌려줘야한다.
            비로그인시 장바구니 담기는 가능하나 결제로 넘어갈때 로그인을 하라고 해주어야한다.
            즉 웹에 데이터를 넘길때 유저값 유무를 체크한후 없을대 로그인창 뿌리고, 있으면 결제로 넘어가며
            비로그인시에는 체크된 어레이 데이터값만 갖고 있는다

            (앱실행시 user데이터가 있어서 자동 로그인 상태일 경우에는 FmList와 MVP에 user데이터에 맞는 리스트가 존재)
            최초 메뉴 생성된 후 에는 FragmentList와 MainViewPage와 연계된 List데이터를 가져와서
            일치하는 AdvVO를 획득, 뿌려준다.

            결제를 누를 경우 웹에 들어가는 형태로 작성, user값 유무를 확인하여 로그인창을 띄우거나, 결제를 진행한다.

            !!!!!!!!!!!!!! 중요한 가정 하나! 장바구니=좋아요리스트 일 경우 무조건 로그인 기반으로 작동되어야 한다.!!!!!!!!!!!!!!!

         */
        getIntentAdvVO();
        createListView();


        //예시로 데이터 넣음 추후 userVO를 통해서 받아와야함.
        /*
        TextView cart_user_name = (TextView)getActivity().findViewById(R.id.cart_user_name);
        cart_user_name.setText(fAdvVO.getAdv_ticker());
        TextView cart_total = (TextView)getActivity().findViewById(R.id.cart_total);
        cart_total.setText(fAdvVO.getAdv_ticker());*/


    }

    public void getIntentAdvVO() {
        Intent intent = getActivity().getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            pLists = bundle.getParcelable(ADVVO_TO_MAIN_VIEW_PAGE);
            lists = pLists.getList_lists();
        }
    }

    public void createListView(){
        fContext = getActivity().getApplicationContext();
        fRecyclerView = (RecyclerView)getActivity().findViewById(R.id.cart_recyclerView);
        //mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<CartListData> listData = new ArrayList<>();

        CartRecyclerAdapter mAdapter = new CartRecyclerAdapter(listData);
        fRecyclerView.setAdapter(mAdapter);


        // 보여주기용 체크리스트
        ArrayList<Boolean> check_list = new ArrayList<>();
        check_list.add(true);
        check_list.add(false);

        /*if(fAdvVO != null) {
            listData.add(new CartListData(R.drawable.product1, fAdvVO.getAdv_title(),"17500", "배송무료", 30, 100, fAdvVO.getAdv_time().getTime(), check_list.get(1)));
        }
        listData.add(new CartListData(R.drawable.product2, fAdvVO.getAdv_title(),"17500", "배송무료", 40, 100, fAdvVO.getAdv_time().getTime(), check_list.get(0)));
        listData.add(new CartListData(R.drawable.product3, fAdvVO.getAdv_title(),"17500", "배송무료", 60, 100, fAdvVO.getAdv_time().getTime(), check_list.get(0)));*/
    }
}