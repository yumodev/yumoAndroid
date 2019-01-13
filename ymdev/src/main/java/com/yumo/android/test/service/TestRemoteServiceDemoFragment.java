package com.yumo.android.test.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 10/8/16.
 */

public class TestRemoteServiceDemoFragment extends YmTestFragment {
    private ServiceConnection mServiceConnection;
    private RemoteServiceDemo.MyBinder myBinder = null;

    /**
     * 开启服务
     */
    public void testStartService(){
        Intent intent = new Intent(getActivity(), RemoteServiceDemo.class);
        getActivity().startService(intent);
    }

    /**
     * 停止服务器
     */
    public void testStopService(){
        Intent intent = new Intent(getActivity(), RemoteServiceDemo.class);
        getActivity().stopService(intent);
    }

    /**
     * 绑定服务
     */
    public void testBindService(){
        if (mServiceConnection != null){
            return;
        }

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (RemoteServiceDemo.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myBinder = null;
            }
        };

        Intent intent = new Intent(getActivity(), RemoteServiceDemo.class);
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
            showToastMessage(myBinder.getHello());
        }
    }

    /**
     * 服务中弹出Toast
     */
    public void testShowToastInBinder(){
        if (myBinder != null){
            myBinder.showToast();
        }
    }
}
