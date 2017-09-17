package com.top_adv.myapplication1.Alarm;

import com.top_adv.myapplication1.DataBaseManagement.DataStream;
import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = MainActivity.TAG + ".AlarmReceiver";

    private AdvStream advStream;

    public class AdvStream extends DataStream<AdvVO> {
    }

    /*
    알람 리시브
알림광고 일때 프로세스 작성해야함


이후 알림 광고 울리면 앱실행되는지 테스트해야함

알림 인텐트 옵션에 앱실행 액션 부여해야함.

     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //알람매니저가 등록된 광고시작 알람을 알려올때
        if (Alarms.ALARM_ALERT_ACTION.equals(intent.getAction())) {
            Log.i(TAG, "ALARM_ALERT_ACTION");
            //알람 인텐트에 데이터가 존재하는지 체크
            if(intent.hasExtra(Alarms.ALARM_INTENT_DATA)) {
                Bundle bundle = intent.getExtras();
                AdvVO advVO = bundle.getParcelable(Alarms.ALARM_INTENT_DATA);
                //알림 등록
                registerNotification(context, advVO);
            } else //없다면 재등록 프로세스 시작
            {
                advStream.readAdvList(list, context);
                if (list == null) {
                    updateDataBase(context, intent);
                }
                int dbNum = Alarms.reRegitAlarm(context, intent, list);
                //알림 등록
                registerNotification(context, list.get(dbNum));
            }
        } else if (Alarms.ALARM_UPDATE_DB_ACTION.equals(intent.getAction())) {
            Log.i(TAG, "ALARM_UPDATE_DB_ACTION"); // 1
            //ALARM_UPDATE_DB_ACTION 즉 00시마다 알려오는 DB update 알람이다.
            //수신되면 DB update를 진행한다.
            updateDataBase(context, intent); // 2

            int period = 40;//ALARM_UPDATE_DB_ACTION 텀을 지정 (원래 24시간이어야 한다.)
            // updateDB 알람이 존재 하지 않을 경우 재등록,
            if (!Alarms.checkAlarm(context, Alarms.ALARM_UPDATE_DB_ACTION, Alarms.ALARM_UPDATE_REQUSET_CODE)) {
                Alarms.setUpdateDB_Alarm(context, period);
            }


        }

    }

    private void registerNotification(Context context, AdvVO advVO) {
        //notification build
        NotificationCompat.Builder cBuild = setNotiyBuilder(context, advVO);
        //notification register
        Log.i(TAG, ".registerNotification() ticker : " + advVO.getAdv_ticker());
        NotificationManager notiymanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notiymanager.notify(111, cBuild.build());

    }

    private NotificationCompat.Builder setNotiyBuilder(Context context, AdvVO advVO) {
        //전달받은 context의 리소스를 획득, 전달받은 alarmData data들 노티빌더에 적용
        NotificationCompat.Builder cBuild = new NotificationCompat.Builder(context);
        cBuild.setSmallIcon(R.drawable.ic_menu_share);
        cBuild.setWhen(System.currentTimeMillis());
        cBuild.setTicker(advVO.getAdv_ticker());
        cBuild.setContentTitle(advVO.getAdv_title());
        cBuild.setContentText(advVO.getAdv_message());
        cBuild.setAutoCancel(true);
        cBuild.setPriority(NotificationCompat.PRIORITY_MAX);

        // 앱을 실행해야 한다.
        ComponentName compName = new ComponentName("com.top_adv.myapplication1","com.top_adv.myapplication1.MainActivity");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(compName);

        context.startActivity(intent);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources r = context.getResources();
        //final Bitmap photo = BitmapFactory.decodeResource(r, advVO.getAdv_resource());
        final Bitmap photo = BitmapFactory.decodeResource(r, R.drawable.ohmygirl);
        //확장형 레이아웃 BigPictureStyle 적용
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(photo);
        style.setBigContentTitle(advVO.getAdv_title());
        style.setSummaryText(advVO.getAdv_message());

        cBuild.setStyle(style);
        cBuild.setContentIntent(pIntent);

        return cBuild;
    }

    public ArrayList<AdvVO> list;

    //ALARM_UPDATE_DB_ACTION을 받으면 서비스와 스레드 생성 후 controlDB.class를 통해
    //서비스에 bind기능을 이용한다면? 기존 동작중인 백그라운드 서비스에 메시지를 날려서
    //controlDB.class를 통해 DB update를 실행 할 수 있을 것이다.
    private void updateDataBase(final Context context, final Intent intent) {
        /*AdvNetWork advNetWork = new AdvNetWork(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                if (params != null) {
                    list = (ArrayList<AdvVO>) params[0];
                    Log.i(TAG, "result data : " + list.get(0).getAdv_title());

                    //받아온 데이터 txt파일로 저장
                    advStream.saveAdvList(list, context); // 3
                    Alarms.setAllDayAlarm(context, list); // 4
                }
            }
        }, null);
        advNetWork.getResult();*/
    }
}

