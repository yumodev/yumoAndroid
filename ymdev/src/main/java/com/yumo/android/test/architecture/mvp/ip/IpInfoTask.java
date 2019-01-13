package com.yumo.android.test.architecture.mvp.ip;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by yumodev on 17/9/7.
 */

public class IpInfoTask implements NetTask<String> {
    private final String LOG_TAG = "mvp,IpInfoTask";

    private IpInfoTask(){}

    public static IpInfoTask getInstance(){
        return IpInfoTaskHolder.mInstance;
    }

    @Override
    public void execute(String data, LoadTasksCallBack callBack) {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(ObservableEmitter<String> subscriber) throws Exception {
                Log.i(LOG_TAG, "call threadName:"+Thread.currentThread().getName());
                for (int i = 0; i < 20; i++){
                    subscriber.onNext(" i = "+i);
                }
                subscriber.onComplete();
            }
        });

        Observer<String> subscriber = new Observer<String>() {
            @Override
            public void onComplete() {
                Log.i(LOG_TAG, "onCompleted");
                Log.i(LOG_TAG, "onCompleted threadName:"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(LOG_TAG, "onError");
            }


            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.i(LOG_TAG, "onNext:"+s);
                Log.i(LOG_TAG, "onNext threadName:"+Thread.currentThread().getName());
            }
        };

        observable.subscribe(subscriber);
    }

    private static final class IpInfoTaskHolder{
        private static final IpInfoTask mInstance = new IpInfoTask();
    }

}
