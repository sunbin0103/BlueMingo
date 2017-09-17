package com.top_adv.myapplication1.FragmentView;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sst on 2016-10-14.
 */
public class FragmentUser extends Fragment {
    private static String TAG = MainActivity.TAG;
    public static final ArrayList<Button> buttons = new ArrayList<>();
    public LinearLayout user_data;
    public static FragmentPagerAdapter adapter;
    public FragmentPurchase fragPurchase;
    public FragmentSetting fragSetting;
    public List<Fragment> fragments_list;

    public FragmentUser() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_user, container, false);

        //user name set in FragmentPurchase
        FragmentPurchase fmPurchase = new FragmentPurchase();
        fragPurchase = fmPurchase;

        FragmentSetting fmSetting = new FragmentSetting();
        fragSetting = fmSetting;

        final List<Fragment> fragments = new ArrayList<>();
        fragments_list = fragments;
        fragments.add(fmPurchase);
        fragments.add(fmSetting);

        FragmentManager fragmentManager = getFragmentManager();
        adapter = new FragmentPagerAdapter(fragmentManager) {
            /*
            @Override
            public CharSequence getPageTitle(int position) {
                return fragments.get(position).getTitle(getActivity());
            }*/

            @Override
            public Fragment getItem(int position) {
                Log.i(TAG,"User.adapter getItem");
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        //getActivity().setTitle(fragments.get(0).getTitle(getActivity()));
        NonSwipeViewPager pager = (NonSwipeViewPager) layout.findViewById(R.id.user_pager);
        pager.setPagingEnabled(false);
        //ViewPager pager = (ViewPager) layout.findViewById(R.id.home_pager);
        /*
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }


            @Override
            public void onPageSelected(int position) {
                //etActivity().setTitle(fragments.get(position).getTitle(getActivity()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/
        pager.setAdapter(adapter);

        user_data = (LinearLayout) layout.findViewById(R.id.user_data);
        user_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick_user_detail(view);
            }
        });

        Button purchase = (Button)layout.findViewById(R.id.btn_user_purchase);
        purchase.setBackgroundColor(getResources().getColor(R.color.clickedMenuButton));
        Button setting = (Button)layout.findViewById(R.id.btn_user_setting);
        setting.setBackgroundColor(getResources().getColor(R.color.defaultMenuButton));

        buttons.add(purchase);
        buttons.add(setting);

        buttons.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick_user(view);
            }
        });
        buttons.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick_user(view);
            }
        });

        Log.i(TAG,"User. onCreateView()");
        return layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
    }


    public void switchFragment(int fragNum) {
        NonSwipeViewPager pager = (NonSwipeViewPager) getActivity().findViewById(R.id.user_pager);
        pager.setCurrentItem(fragNum);
        //adapter.notifyDataSetChanged();

        /*
        FragmentManager fmManager = getFragmentManager();
        FragmentTransaction fmTransaction = fmManager.beginTransaction();
        fmTransaction.add(R.id.main_fragment_view, frag);
        fmTransaction.commit();*/
    }

    public void onButtonClick_user_detail(View view) {
        int adv_key = view.getId();
        FragmentRes_Detail fragmentRes_detail = new FragmentRes_Detail();
        Bundle bundle = new Bundle();
        bundle.putInt("adv_key",adv_key);
        fragmentRes_detail.setArguments(bundle);
        fragments_list.add(fragmentRes_detail);
        adapter.notifyDataSetChanged();
        switchFragment(2);
    }

    public void onButtonClick_user(View view) {
        int fragNum = 0;
        switch (view.getId()) {
            case R.id.btn_user_purchase :
                buttons.get(0).setBackgroundColor(getResources().getColor(R.color.clickedMenuButton));
                buttons.get(1).setBackgroundColor(getResources().getColor(R.color.defaultMenuButton));
                fragNum = 0;
                break;
            case R.id.btn_user_setting :
                buttons.get(1).setBackgroundColor(getResources().getColor(R.color.clickedMenuButton));
                buttons.get(0).setBackgroundColor(getResources().getColor(R.color.defaultMenuButton));
                fragNum = 1;
                break;
        }
        switchFragment(fragNum);
    }
}
