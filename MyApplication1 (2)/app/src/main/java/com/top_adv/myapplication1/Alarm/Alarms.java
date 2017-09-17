package com.top_adv.myapplication1.Alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.Log;

public class Alarms {
	private static final String TAG = MainActivity.TAG+".Alarms";
	
	//알람매니저에 등록된 알람을 알려올때
	//기본 광고알람 - 노티 등록, 다음 알람 등록
	public static final String ALARM_ALERT_ACTION = "com.example.top_adv.ALARM_ALERT";
	//00시 DB update 알람
	public static final String ALARM_UPDATE_DB_ACTION = "com.example.top_adv.UPDATE_DB";

	//DataBase에서 가져오고 알람매니저를 통해 전달될 값들
	//Parcelabel로 만들어진 data객체 (resource,uri,notiy-title,messgae,ticker)
	public static final String ALARM_INTENT_DATA = "alarm.intent.data";


	public static final String ALARM_REQUEST_CODE = "alarm.request.code";
	public static final int ALARM_FIRST_DB_NUMBER = 0;
	public static final int ALARM_UPDATE_REQUSET_CODE = 0xa;
	public static final int ALARM_ALERT_REQUSET_CODE = 0xb;
	//test uri
	private static final String TEST_URI = "http://www.naver.com";


	public static void setAlertAlarm(Context context,AdvVO advVO, int requestCode)
	{
		Intent alarmIntent = new Intent(ALARM_ALERT_ACTION);
		alarmIntent.putExtra(ALARM_INTENT_DATA,advVO);
		alarmIntent.putExtra(ALARM_REQUEST_CODE,requestCode);

		PendingIntent pIntent = PendingIntent.getBroadcast(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		//intent->알람 등록
		int time = 10*1000;
		//test용 값 서비스는 60초동안 유지, updateDB_Alarm은 60초간격 반복, ALERT_ALARM은 10초 간격 1,2,3후 중단
		Log.i(TAG, ".setAlaertAlarm() Code : "+requestCode);
		AlarmManager alarmM = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmM.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+time, pIntent);

	}

	public static void setUpdateDB_Alarm(Context context, int period)
	{
		//***** 노티는 같은 서비스 혹은 앱에서 호출될경우 중복이 되는가 안되는가? 노티는 쌓여있어야 한다.
		//AlarmManager를 사용하여 등록한다.
		Log.i(TAG, ".setUpdateDB_Alarm()");
		AlarmManager alarmM = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(Alarms.ALARM_UPDATE_DB_ACTION);

		PendingIntent pIntent = PendingIntent.getBroadcast(context, ALARM_UPDATE_REQUSET_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Log.i(TAG, "Alarms.setUpdateDB_Alarm().current + 10 + "+period);
		//alarmM.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() ,70*1000, pIntent);
		alarmM.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10*1000+(period*1000), pIntent);


		try
		{
			// 00시에 처음 시작해서, 24시간 마다 실행되게
			Date now = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse("2010-01-01- 00:00:00");

			Log.i(TAG, "Alarms.setUpdateDB_Alarm()_setInexactRepeating_2");
			alarmM.setInexactRepeating(AlarmManager.RTC, now.getTime() ,24*60*60*1000, pIntent);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public static void setAllDayAlarm(Context context, ArrayList<AdvVO> list){
		for(int i=0; i<list.size();i++){
			setAlertAlarm(context,list.get(i),ALARM_ALERT_REQUSET_CODE+i);
		}
	}

	//알람 재등록을 위해 알람체크 후 등록, DBnum구하는 함수
	public static int reRegitAlarm(Context context, Intent intent, ArrayList<AdvVO> list){
		int dbNum = 0;
		Boolean checkAlarm = null;
		if (intent.hasExtra(Alarms.ALARM_REQUEST_CODE)) {
			intent.getIntExtra(ALARM_REQUEST_CODE, dbNum);
			dbNum =- ALARM_ALERT_REQUSET_CODE;
		}
		else {
			TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
			Calendar cal = new GregorianCalendar(zone).getInstance();
			long  now_time = cal.getTimeInMillis();

			long compare = 0;
			long temp = Math.abs(now_time - list.get(0).getAdv_time());
			for(int i=1; i<list.size(); i++){
				compare = Math.abs(now_time - list.get(i).getAdv_time());
				if(temp > compare){
					temp = compare;
					dbNum = i;
				}
			}
		}

		//다음 알람 유무 체크
		if(!checkAlarm(context,ALARM_ALERT_ACTION,ALARM_ALERT_REQUSET_CODE + dbNum+1)){
			setAlertAlarm(context,list.get(dbNum+1),ALARM_ALERT_REQUSET_CODE + dbNum+1);
		}

		return dbNum;
	}

	public static void releaseAlarm(Context context, String action, int requestCode)
	{
		Intent alarmIntent = new Intent(action);

		Log.i(TAG, ".releaseAlarm Code : "+requestCode);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

		PendingIntent cancel = PendingIntent.getBroadcast(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		if(cancel != null){
			alarmManager.cancel(cancel);
			cancel.cancel();
		}

		// 주석을 풀면 먼저 실행되는 알람이 있을 경우, 제거하고
		// 새로 알람을 실행하게 된다. 상황에 따라 유용하게 사용 할 수 있다.
		//alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 3000, pIntent);
	}

	public static Boolean checkAlarm(Context context, String action, int requestCode)
	{
		Intent alramIntent = new Intent(action);
		PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, alramIntent, PendingIntent.FLAG_NO_CREATE);

		if (sender != null) {
			// TODO: 이미 설정된 알람이 있는 경우
			sender.cancel();
			Log.i(TAG,"already exist Code : "+requestCode);
			return true;
		} else {
			// TODO: 이미 설정된 알람이 없는 경우
			Log.i(TAG,"not exist Code : "+requestCode);
			return false;
		}
	}

}