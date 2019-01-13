package com.yumo.android.test.architecture.mvp.ip;

/**
 * Created by yumodev on 17/9/7.
 */

public interface NetTask<T> {
    void execute(T data, LoadTasksCallBack callBack);
}
