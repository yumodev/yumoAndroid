package com.yumo.android.test.architecture;

import android.arch.lifecycle.LifecycleRegistry;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yumo.common.log.Log;
import com.yumo.common.util.Reflect;
import com.yumo.demo.view.YmTestFragment;

import java.util.Map;

public class TestLifeCycle extends YmTestFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new YmLifeCycle());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LifecycleRegistry registry = (LifecycleRegistry) getLifecycle();

        Map observerMap =  (Map)(Reflect.on(registry).field("mObserverMap").get("mHashMap"));
        for (Object key : observerMap.keySet()){
            Log.i("TestLifeCycle", key.getClass().getName());
            if (key instanceof YmLifeCycle){
                YmLifeCycle.class.cast(key).onConfigurationChanged(newConfig);
            }
        }
    }
}
