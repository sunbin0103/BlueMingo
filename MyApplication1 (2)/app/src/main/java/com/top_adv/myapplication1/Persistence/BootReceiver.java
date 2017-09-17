package com.top_adv.myapplication1.Persistence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.top_adv.myapplication1.MainActivity;


public class BootReceiver extends BroadcastReceiver{
	private static final String TAG = MainActivity.TAG;
	private static final String BOOT_SIGNAL = "android.permission.RECEIVE_BOOT_COMPLETED";
	private static final String ADV_SERVICE = "com.example.top_adv.Adv_Service";
	public static final String BOOT_START_ON = "com.example.top_adv.BOOT_START_ON";
	public static final String BOOT_START_OFF = "com.example.top_adv.BOOT_START_OFF";
	private static String BOOT = BOOT_START_ON;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/*
		if(intent.getAction().equals(intent.ACTION_BOOT_COMPLETED))
		{
			
			
			if(BOOT.equals(BOOT_START_ON))
			{
				Log.i(TAG,"BOOT ON, Top_adv Service start");
				Toast.makeText(context, "BOOT, Top_adv Service start", Toast.LENGTH_SHORT).show();
				context.startService(new Intent(ADV_SERVICE));
			}
			else
			{
				Log.i(TAG,"BOOT, Top_adv Service not start");
				Toast.makeText(context, "BOOT OFF, Top_adv Service not start", Toast.LENGTH_SHORT).show();
				
			}
			
		}*/
	}
	
	public static void bootStart(String boot)
	{
		BOOT = boot; 
	}

}
