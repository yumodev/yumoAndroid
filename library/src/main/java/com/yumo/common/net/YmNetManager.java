package com.yumo.common.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.yumo.common.android.YmContext;

import java.util.ArrayList;
import java.util.List;

public class YmNetManager extends BroadcastReceiver {

    private List<NetStateChangeObserver> mObservers = new ArrayList<>();
    private YmNetType mNetType;
    private static class InstanceHolder {
        private static final YmNetManager INSTANCE = new YmNetManager();
    }

    private YmNetManager(){
        mNetType = YmNetUtils.getNetworkType(YmContext.getAppContext());
    }

    public static YmNetManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            YmNetType netType = YmNetUtils.getNetworkType(context);
            notifyObservers(netType);
        }
    }

    public YmNetType getCurrentType(){
        return mNetType;
    }

    /**
     * 注册网络监听
     */
    public void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(getInstance(), intentFilter);
    }

    /**
     * 取消网络监听
     */
    public void unregisterReceiver(@NonNull Context context) {
        context.unregisterReceiver(getInstance());
    }

    /**
     * 注册网络变化Observer
     */
    public void registerObserver(NetStateChangeObserver observer) {
        if (observer == null)
            return;
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    /**
     * 取消网络变化Observer的注册
     */
    public void unregisterObserver(NetStateChangeObserver observer) {
        if (observer == null || mObservers == null){
            return;
        }

        mObservers.remove(observer);
    }

    /**
     * 通知所有的Observer网络状态变化
     */
    private void notifyObservers(YmNetType netType) {
        mNetType = netType;
        if (netType == YmNetType.NETWORK_NO || netType == YmNetType.NETWORK_UNKNOWN) {
            for(NetStateChangeObserver observer : mObservers) {
                observer.onNetDisconnected();
            }
        } else {
            for(NetStateChangeObserver observer : mObservers) {
                observer.onNetConnected(netType);
            }
        }
    }

    /**
     * 网络状态变化观察者
     */
 public interface NetStateChangeObserver {

        void onNetDisconnected();

        void onNetConnected(YmNetType networkType);
    }

}


