package com.top_adv.myapplication1.Persistence;

import com.top_adv.myapplication1.Alarm.Alarms;
import com.top_adv.myapplication1.MainActivity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;


public class Adv_Service extends Service {
	private static final String TAG = MainActivity.TAG;


	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;

	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {

			// Normally we would do some work here, like download a file.
			// For our sample, we just sleep for 5 seconds.
			long endTime = System.currentTimeMillis() + 70*1000;
			while (System.currentTimeMillis() < endTime) {
				synchronized (this) {
					try {
						wait(endTime - System.currentTimeMillis());
					} catch (Exception e) {
					}
				}
			}
			// Stop the service using the startId, so that we don't stop
			// the service in the middle of handling another job


			//release All alarm before service end
			//Alarms.releaseAlarm(getApplicationContext(),Alarms.ALARM_UPDATE_DB_ACTION);
			//Alarms.releaseAlarm(getApplicationContext(),Alarms.ALARM_ALERT_ACTION);
			//service end
			stopSelf(msg.arg1);


			//위에서 수행하는 while문은 n초 wait 했다가 끝난다.
			//stopSelf()는 전달받은 msg에 arg1 값을 통해 서비스 종료를 수행한다.
			//이는 onStartCommand()에서 msg를 보냈기 때문이다.
			//arg1은 서비스의 startId 이다.
		}
	}

	@Override
	public void onCreate() {
		// Start up the thread running the service.  Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block.  We also smake it
		// background priority so CPU-intensive work will not disrupt our UI.
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		// Get the HandlerThread's Looper and use it for our Handler
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG,"service starting");

		// For each start request, send a message to start a job and deliver the
		// start ID so we know which request we're stopping when we finish the job


		//1. 부팅시 BroadCastReciver를 통해서 서비스 시작
		//2. 최초 알림등록(00시에는 서버체크, DB update)
		/*if(!Alarms.checkAlarm(this, Alarms.ALARM_REQUEST_CODE))
		{//not exist ALARM_UPDATE_DB_ACTION
			Alarms.setUpdateDB_Alarm(this,0);
		}
		else
		{

		}*/

		//3. 현재 시간과 가장 가까운 알람DB확인, 해당 알람 등록(루틴 시작)
		//4. 알람 루틴 *******************************************
		//	알람1 시작-> notiy(db1)-> 알람2 등록-> db끝->루틴 종료
		//
		//5. 알람은 2개가 동시 작동중 (1.00시 서버체크알람, 2.알람 루틴)


		Message waitStop_msg = mServiceHandler.obtainMessage();
		waitStop_msg.arg1 = startId;
		mServiceHandler.sendMessage(waitStop_msg);
		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// We don't provide binding, so return null
		return null;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG,"service done");
	}

	public void cancelService(int startId)
	{
		//service 종료를 어떻게 할 것인가?
		Message waitStop_msg = mServiceHandler.obtainMessage();
		waitStop_msg.arg1 = startId;
		mServiceHandler.sendMessage(waitStop_msg);
	}

}