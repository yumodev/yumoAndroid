/**
 * ServiceDemo.java
 * yumo
 * 2015-1-25
 * 一个ServiceDemo
 */
package com.yumo.android.test.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.yumo.common.android.YmContext;

/**
 * yumo
 *
 */
public class ServiceDemo extends Service {
	private static final String TAG = "ServiceDemo";

	public MyBinder mBinder = new MyBinder();
	public ServiceDemo() {
		Log.i(TAG, "ServiceDemo");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG,"onStartCommand");
		showToast("onStartCommand");
        //showSystemDialog();
		return super.onStartCommand(intent, flags, startId);
	}

	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	private void showSystemDialog(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(YmContext.getAppContext())
                .setTitle("alertDialog")
                .setMessage("message")
                .setPositiveButton("cancel", null)
                .setNegativeButton("ok", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "onUnBind");
		return super.onUnbind(intent);
	}
	
	@Override
	public boolean bindService(Intent service, ServiceConnection conn, int flags) {
		Log.d(TAG, "bindService");
		return super.bindService(service, conn, flags);
	}

	@Override
	public void unbindService(ServiceConnection conn) {
		Log.d(TAG, "unbindService");
		super.unbindService(conn);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}

	static class MyBinder extends Binder {
		public String getHello(){
			printBinderInfo();
			return "hello world";

		}

		public void printBinderInfo(){
			StringBuilder sb = new StringBuilder();
			sb.append("ThreadName:"+Thread.currentThread().getName());
			sb.append("MainThreadName:"+ Looper.getMainLooper().getThread().getName());
			Log.i(TAG, sb.toString());
		}

		public void showToast(){
			Toast.makeText(YmContext.getAppContext(), "toast in binder", Toast.LENGTH_SHORT).show();
		}
	}
	
}
