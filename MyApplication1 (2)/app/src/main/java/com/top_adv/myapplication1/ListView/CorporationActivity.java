package com.top_adv.myapplication1.ListView;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.top_adv.myapplication1.DataVO.FaqVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by EunBin Lee on 2017-04-11.
 */

public class CorporationActivity extends AppCompatActivity {

    private static String TAG = MainActivity.TAG;

    private GridLayout faq_btn_layout;
    private static Button[] faq_button_list;
    private static String[] faq_button_category = {"공동구매/주문","반품/교환/환불","배송/이용 문의","회원정보"};

    private TextView faq_inform_time;
    private TextView faq_inform_call;

    private ExpandableListView faq_list;
    private FaqExpandListAdapter faqAdapter;
    private ArrayList<FaqVO> faqData;
    private ArrayList<FaqVO> faqRealData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_faq);

        faq_btn_layout = (GridLayout) findViewById(R.id.faq_btn_layout);

        faq_button_list = new Button[4];
        for(int i=0; i<faq_button_list.length; i++) {
            faq_button_list[i] = new Button(this);
            faq_button_list[i].setText(faq_button_category[i]);
            faq_button_list[i].setWidth(GridLayout.LayoutParams.MATCH_PARENT);
            faq_button_list[i].setId(i);

            faq_button_list[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    TextView tv = (TextView) view;
                    tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tv.setTextColor(getResources().getColor(R.color.text_card_list));
                    */
                    setFaqList(view.getId());
                }
            });

            faq_btn_layout.addView(faq_button_list[i]);
        }

        faq_list = (ExpandableListView) findViewById(R.id.faq_list);
        faq_list.setGroupIndicator(null); // 그룹 왼쪽에 기본 제공되는 화살표 삭제

        faq_inform_time = (TextView) findViewById(R.id.faq_inform_time);
        faq_inform_call = (TextView) findViewById(R.id.faq_inform_call);

        faq_inform_time.setText("고객센터 (운영시간 AM 10:00 ~ PM 6:00)");
        faq_inform_call.setText("카카오 @bluemingo\n이메일 CS @bluemingo.com\n전화문의 1599-1234");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.
        actionBar.setElevation(0);                          //그림자 없애기.
        actionBar.setBackgroundDrawable(new ColorDrawable(0x00000000));

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.action_bar, null);

        TextView title = (TextView) actionbar.findViewById(R.id.action_bar_title);
        title.setText("자주 묻는 질문");

        actionBar.setCustomView(actionbar);
        return true;
    }

    public void actionBarClick (View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public void setFaqList(int i) {
        faqData = new ArrayList<FaqVO>();

        BufferedReader in;
        Resources myResources = getResources();
        InputStream myFile = null;

        switch (i) {
            case 0:
                myFile = myResources.openRawResource(R.raw.faq_purchase);
                break;
            case 1:
                myFile = myResources.openRawResource(R.raw.faq_exchange);
                break;
            case 2:
                myFile = myResources.openRawResource(R.raw.faq_delivery);
                break;
            case 3:
                myFile = myResources.openRawResource(R.raw.faq_userdata);
                break;
        }

        StringBuffer strBuffer = new StringBuffer();
        String str = null;

        try {
            in = new BufferedReader(
                    new InputStreamReader(myFile, "UTF-8"));  // file이 utf-8 로 저장되어 있다면 "UTF-8"
            while( (str = in.readLine()) != null)             // file이 KSC5601로 저장되어 있다면 "KSC5601"
            {
                strBuffer.append(str + "\n");
            }
            in.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String contents = strBuffer.toString();
        StringTokenizer tokenizer = new StringTokenizer(contents);

        String faq_title = null;
        String faq_detail = null;

        while (tokenizer.hasMoreTokens()) {
            faq_title = tokenizer.nextToken("\n");
            faq_detail = tokenizer.nextToken("\n\n\n");

            faqData.add(new FaqVO(faq_title, faq_detail));
        }

        faqAdapter = new FaqExpandListAdapter(getApplicationContext(), faqData);
        faq_list.setAdapter(faqAdapter);

        faqAdapter.notifyDataSetChanged();
    }
}
