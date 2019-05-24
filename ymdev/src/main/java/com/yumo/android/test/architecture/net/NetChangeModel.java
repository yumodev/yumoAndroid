package com.yumo.android.test.architecture.net;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.res.Configuration;

import com.yumo.common.net.YmNetManager;
import com.yumo.common.net.YmNetType;
import com.yumo.ui.arch.YmLifeCycle;

public class NetChangeModel extends ViewModel implements YmLifeCycle, YmNetManager.NetStateChangeObserver{
    private MutableLiveData<YmNetType> mNetType = new MutableLiveData<>();

    public void init(YmNetType ymNetType) {
        mNetType.setValue(ymNetType);
    }

    public MutableLiveData<YmNetType> getNetType(){
        return mNetType;
    }


    @Override
    public void create(LifecycleOwner lifecycleOwner) {
        YmNetManager.getInstance().registerObserver(this);
    }

    @Override
    public void start(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void resume(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void pause(LifecycleOwner lifecycleOwner) {
    }

    @Override
    public void stop(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void destroy(LifecycleOwner lifecycleOwner) {
        YmNetManager.getInstance().unregisterObserver(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onNetDisconnected() {
        mNetType.setValue(YmNetType.NETWORK_NO);
    }

    @Override
    public void onNetConnected(YmNetType networkType) {
        mNetType.setValue(networkType);
    }
}
