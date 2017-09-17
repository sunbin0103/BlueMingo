package com.top_adv.myapplication1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sst on 2016-10-07.
 */
public class TestReceiver extends BroadcastReceiver {

    private static String MAIN_VIEW_PAGE = "com.top_adv.myapplication1.MainViewPage.go";

    @Override
    public void onReceive(Context context, Intent intent) {

        String name = intent.getAction();

        if(name.equals(MAIN_VIEW_PAGE)){
            Log.i("receiver","get receiver");

        }
    }
}
