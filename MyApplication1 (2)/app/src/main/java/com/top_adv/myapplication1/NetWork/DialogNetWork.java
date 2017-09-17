package com.top_adv.myapplication1.NetWork;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.top_adv.myapplication1.R;

/**
 * Created by sst on 2016-12-15.
 */
public class DialogNetWork extends Dialog {

    public DialogNetWork(Context context){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_net);
    }

}
