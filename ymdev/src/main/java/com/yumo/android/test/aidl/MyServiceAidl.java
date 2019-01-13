/**
 * MyServiceAidl.java
 * yumo
 * 2015-3-13
 * TODO
 */
package com.yumo.android.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * yumodev
 * 主要用来测试和联系Aidl来使用
 */
public class MyServiceAidl extends Service {
	
	private final String TAG = "MyServiceAidl";
	
	//定义Bindler
	
	public final IBinder mBinder = new IMyServiceAidl.Stub() {
		
		private String aidlInfo = "ImyServiceAidl";
		
		@Override
		public void setAidlInfo(String aidl) throws RemoteException {
			Log.i(TAG,"mBinder, setAidlInfo");
			aidlInfo = aidl;
		}
		
		@Override
		public String getAidlInfo() throws RemoteException {
			Log.i(TAG, "mBinder, getAidlInfo");
			return "getAidlInfo result";
		}
		
		@Override
		public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
				throws RemoteException {
			return super.onTransact(code, data, reply, flags);
		}
	};

	public MyServiceAidl() {
	}


	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
