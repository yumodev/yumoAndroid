package com.yumo.android.test.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.yumo.android.test.aidl.IMyServiceAidl;
import com.yumo.android.test.aidl.MyServiceAidl;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/8/7.
 */

public class AidlServiceDemoFragment extends YmTestFragment {

    private ServiceConnection mServiceConnection;
    private IMyServiceAidl myBinder = null;
    public void testStartService(){
        Intent intent = new Intent(getActivity(), MyServiceAidl.class);
        getActivity().startService(intent);
    }

    public void testSoopService(){
        Intent intent = new Intent(getActivity(), MyServiceAidl.class);
        getActivity().stopService(intent);
    }

    public void testBindService(){
        if (mServiceConnection != null){
            return;
        }

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = IMyServiceAidl.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myBinder = null;
            }
        };

        Intent intent = new Intent(getActivity(), MyServiceAidl.class);
        getActivity().bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    /*
    * 解绑服务。
    */
    public void testUnBindServe(){
        //Intent intent = new Intent(getActivity(), ServiceDemo.class);
        getActivity().unbindService(mServiceConnection);
        mServiceConnection = null;
    }

    /**
     * 服务中弹出Toast
     */
    public void testShowHello(){
        if (myBinder != null){
            try {
                showToastMessage(myBinder.getAidlInfo());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
