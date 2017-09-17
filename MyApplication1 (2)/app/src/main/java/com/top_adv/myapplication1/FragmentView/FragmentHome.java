package com.top_adv.myapplication1.FragmentView;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sst on 2016-10-14.
 */
public class FragmentHome extends Fragment {
    private static String TAG = MainActivity.TAG;
    public static FragmentPagerAdapter adapter;
    public FragmentList fragList;
    public FragmentUser fragUser;
    public List<Fragment> fragments_list;

    public Preference corporation;

    public FragmentHome() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentList fmList = new FragmentList();
        fragList = fmList;
        FragmentUser fmUser = new FragmentUser();
        fragUser = fmUser;

        final List<Fragment> fragments = new ArrayList<>();
        fragments_list = fragments;
        fragments.add(fmList);
        fragments.add(fmUser);
        Log.i(TAG,"Home. onCreateView()");

        adapter = new FragmentPagerAdapter(getActivity().getFragmentManager()) {
            /*
            @Override
            public CharSequence getPageTitle(int position) {
                return fragments.get(position).getTitle(getActivity());
            }*/

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        //getActivity().setTitle(fragments.get(0).getTitle(getActivity()));
        NonSwipeViewPager pager = (NonSwipeViewPager) layout.findViewById(R.id.home_pager);
        pager.setPagingEnabled(false);
        /*
        ViewPager pager = (ViewPager) layout.findViewById(R.id.home_pager);

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
/*
        corporation = fmUser.fragSetting.getCorporation();
        corporation.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                switchFragment(2);
                return true;
            }
        });
*/
        return layout;
    }

    public void switchFragment(int fragNum) {
        NonSwipeViewPager pager = (NonSwipeViewPager) getActivity().findViewById(R.id.home_pager);
        pager.setCurrentItem(fragNum);
        //adapter.notifyDataSetChanged();
        /*
        FragmentManager fmManager = getFragmentManager();
        FragmentTransaction fmTransaction = fmManager.beginTransaction();
        fmTransaction.add(R.id.main_fragment_view, frag);
        fmTransaction.commit();*/
    }

    public void onPreferenceClick_faq() {
        switchFragment(2);
    }
}
