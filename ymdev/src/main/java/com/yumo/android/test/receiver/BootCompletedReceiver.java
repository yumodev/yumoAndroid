/**
 * BootCompletedReceiver.java
 * yumodev
 * 2015-3-15
 */
package com.yumo.android.test.receiver;

import com.yumo.android.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * yumodev
 * 开机启动广播
 */
public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction() == Intent.ACTION_BOOT_COMPLETED){
			Intent intentRoot = new Intent(context, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intentRoot);
		}
	}

}
