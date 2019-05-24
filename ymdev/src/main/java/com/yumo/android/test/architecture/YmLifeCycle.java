package com.yumo.android.test.architecture;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.res.Configuration;

import com.yumo.common.log.Log;


public class YmLifeCycle implements LifecycleObserver {
    private static final String TAG = YmLifeCycle.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create(LifecycleOwner lifecycleOwner) {
        Lifecycle.State currentState = lifecycleOwner.getLifecycle().getCurrentState();
        Log.i(TAG, "create: " + currentState);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start(LifecycleOwner lifecycleOwner) {
        Log.i(TAG, "start: " + lifecycleOwner.getLifecycle().getCurrentState());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume(LifecycleOwner lifecycleOwner) {
        Log.i(TAG, "resume: " + lifecycleOwner.getLifecycle().getCurrentState());
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause(LifecycleOwner lifecycleOwner) {
        Log.i(TAG, "pause: " + lifecycleOwner.getLifecycle().getCurrentState());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(LifecycleOwner lifecycleOwner) {
        Log.i(TAG, "stop: " + lifecycleOwner.getLifecycle().getCurrentState());
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy(LifecycleOwner lifecycleOwner) {
        Log.i(TAG, "destroy: " + lifecycleOwner.getLifecycle().getCurrentState());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void any(LifecycleOwner lifecycleOwner) {
        Log.i(TAG, "any: " + lifecycleOwner.getLifecycle().getCurrentState());
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, "onConfigurationChanged: " + newConfig.orientation);
    }
}
