package com.top_adv.myapplication1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.top_adv.myapplication1.FragmentView.FragmentHome;
import com.top_adv.myapplication1.FragmentView.FragmentSetting;

import java.util.ArrayList;

public class MainViewPage extends AppCompatActivity{
    private static String TAG = MainActivity.TAG;
    private static String ADVVO_TO_MAIN_VIEW_PAGE = "main_view_page.advVO";

    private FragmentHome fmHome;
    private ArrayList<ImageButton> buttons;

    //public DBManagement advDataBase = new DBManagement(this.getApplicationContext(), "Advertise.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_page);

        createFragmentView();
        setButtons();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //createListView();
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    public void createFragmentView() {
        Bundle bundle = new Bundle();
        //FragmentList fmList = new FragmentList();
        //fmList.setArguments(bundle);
        fmHome = new FragmentHome();
        fmHome.setArguments(bundle);
        Log.i(TAG,"fragment new");

        FragmentManager fmManager = getFragmentManager();

        FragmentTransaction fmTransaction = fmManager.beginTransaction();
        fmTransaction.add(R.id.main_fragment_view, fmHome);
        Log.i(TAG,"fragment add");
        fmTransaction.commit();

    }

    public void setButtons() {
        /*buttons = new ArrayList<>();
        buttons.add((Button)findViewById(R.id.btn_home));
        buttons.add((Button)findViewById(R.id.btn_user));
        buttons.add((Button)findViewById(R.id.btn_faq));
        buttons.add((Button)findViewById(R.id.btn_cart));

        buttons.get(0).setBackgroundColor(getResources().getColor(R.color.clickedMenuButton));
        for(int i=1; i<4; i++) {
            buttons.get(i).setBackgroundColor(getResources().getColor(R.color.defaultMenuButton));
        }*/

    }

    public void onButtonClick_Bar(View view) {
        int fragNum = 0;
        switch (view.getId()) {
            case R.id.btn_home :
                fragNum = 0;
                break;
            case R.id.btn_user :
                fragNum = 1;
                break;
        }
        fmHome.switchFragment(fragNum);
        /*buttons.get(fragNum).setBackgroundColor(getResources().getColor(R.color.clickedMenuButton));
        for(int i=0; i<4; i++) {
            if(i != fragNum) {
                buttons.get(i).setBackgroundColor(getResources().getColor(R.color.defaultMenuButton));
            }
        }*/
    }
}
