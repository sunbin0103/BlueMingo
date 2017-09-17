package com.top_adv.myapplication1.FragmentView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.top_adv.myapplication1.DataVO.UserVO;
import com.top_adv.myapplication1.ListView.CorporationActivity;
import com.top_adv.myapplication1.Login.UserModifyActivity;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

/**
 * Created by sst on 2016-10-18.
 */
public class FragmentSetting extends PreferenceFragment {

    private static String TAG = MainActivity.TAG;

    private UserVO userVO;

    private PreferenceScreen screen;
    private SwitchPreference key_switch_push_alarm;
    private Preference key_corporation;
    private Preference key_user_data;
    private Preference key_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_user_setting);

        screen = getPreferenceScreen();

        key_corporation = screen.findPreference("key_corporation");
        key_switch_push_alarm = (SwitchPreference) screen.findPreference("key_switch_push_alarm");
        key_user_data = screen.findPreference("key_user_data");
        key_logout = screen.findPreference("key_logout");

        key_corporation.setTitle("고객센터");
        key_switch_push_alarm.setTitle("앱 푸시 알람");
        key_switch_push_alarm.setSummary("공동구매 안내를 위해 알림을 켜두는 것이 좋습니다.");
        key_switch_push_alarm.setSelectable(true);
        key_user_data.setTitle("회원 정보");
        key_logout.setTitle("로그아웃");

        key_corporation.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(preference.getContext(), CorporationActivity.class);
                startActivity(intent);
                return false;
            }
        });

        key_user_data.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(preference.getContext(), UserModifyActivity.class);
                startActivity(intent);
                return false;
            }
        });

        key_logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // 여기서 로그아웃
                return false;
            }
        });
    }
}
