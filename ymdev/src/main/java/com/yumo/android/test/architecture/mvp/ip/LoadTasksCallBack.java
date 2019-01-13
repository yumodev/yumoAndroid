package com.yumo.android.test.architecture.mvp.ip;

/**
 * Created by yumodev on 17/9/7.
 */

public interface LoadTasksCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}
