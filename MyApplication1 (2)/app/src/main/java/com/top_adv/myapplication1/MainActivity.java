package com.top_adv.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.OrderVO;
import com.top_adv.myapplication1.Login.LoginActivity;
import com.top_adv.myapplication1.NetWork.AsyncCallBack;
import com.top_adv.myapplication1.NetWork.JsonParsing;
import com.top_adv.myapplication1.NetWork.NetWorkConnectionImpl;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "Top_adv";
    public static AppCompatActivity activity;
    private ProgressBar mPgb;
    public TextView retest;

    public ArrayList<ListVO> lists;
    public AdvVO advVO;
    public OrderVO orderVO;

    private JsonParsing jsonParsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;
        retest = (TextView) findViewById(R.id.retest);

        mPgb = (ProgressBar) findViewById(R.id.custom_progressBar);

        Button go_mainViewPage = (Button) findViewById(R.id.button_goMainViewPage);
        go_mainViewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 버튼 클릭시 로그인 프로세스 실행, 수신된 데이터 PendingIntent로 MainViewPage.activity로 넘김
                 * 로그인 프로세스에서 ProgressBar(Round형태)와 함게 데이터 송수신 작동하도록 할 것
                 */

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                activity.finish();

                //updateDataBase(getApplicationContext());

            }
        });
    }


    public ArrayList<AdvVO> list;
    //ALARM_UPDATE_DB_ACTION을 받으면 서비스와 스레드 생성 후 controlDB.class를 통해
    //서비스에 bind기능을 이용한다면? 기존 동작중인 백그라운드 서비스에 메시지를 날려서
    //controlDB.class를 통해 DB update를 실행 할 수 있을 것이다.
    private void updateDataBase(final Context context)
    {
        /*
        AdvNetWork advNetWork = new AdvNetWork(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                if(params != null) {
                    list = (ArrayList<AdvVO>) params[0];
                    System.out.println("result_data : " + list.get(0).getAdv_title());

                    saveAdvList(list,context);


                    readAdvList(context);
                }
            }
        }, null);
        advNetWork.getResult();
        */

        advVO = new AdvVO();
        jsonParsing = new JsonParsing(AdvVO.class);

        NetWorkConnectionImpl network = new NetWorkConnectionImpl(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                if(params != null) {
                    list = (ArrayList<AdvVO>) params[0];
                    System.out.println("result_data : " + list.get(0).getAdv_title());

                    saveAdvList(list,context);


                    readAdvList(context);
                }
            }
        }, MainActivity.this) {};
        network.setMethod("android/getAdvList",false,false,"GET");
        network.getNetWorkResult();
    }

    private void saveAdvList(ArrayList<AdvVO> list, Context context){
        String fileName = "adv_list.txt";
        try {
            FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(list);
            //oos.flush();​

            System.out.println("save list");
            if(oos != null){
                oos.close();
            }
            if(fout != null){
                fout.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee){
            ee.printStackTrace();
        }
    }

    private void readAdvList(Context context){
        String fileName = "adv_list.txt";
        try {
            System.out.println("read list");

            FileInputStream fin = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            list = (ArrayList<AdvVO>) ois.readObject();

            retest.setText(list.get(0).getAdv_title());
            if(ois != null){
                ois.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee){
            ee.printStackTrace();
        }
    }



/* 네트워크 사용 예시
                ListNetWork listNetWork = new ListNetWork(new AsyncCallBack() {
                    @Override
                    public void onTaskDone(Object... params) {
                        if(params != null) {
                            list = (ArrayList<ListVO>) params[0];
                            System.out.println("result_data : " + list.get(0).getAdv_title());
                            retest.setText(list.get(0).getAdv_title());
                        }
                    }
                }, MainActivity.this);
                listNetWork.getResult();

                TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
                Calendar cal = new GregorianCalendar(zone).getInstance();
                long  now_time = cal.getTimeInMillis();

                orderVO = new OrderVO();
                orderVO.setRes_type(1);
                orderVO.setPurchase(3,4,2,8,now_time);

                OrderNetWork orderNetWork = new OrderNetWork(new AsyncCallBack() {
                    @Override
                    public void onTaskDone(Object... parmas) {
                        System.out.println("order result : "+parmas[0]);
                    }
                });
                orderNetWork.setOrderVO(orderVO);
                orderNetWork.getResult();
 */

}
