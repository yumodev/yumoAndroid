package com.yumodev.rxjava2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yumodev on 17/10/12.
 * 两种观察者模式
 * Observable(被观察者)/Observer(观察者) 不支持背压。
 * Flowable(被观察者)/Subscriber(观察者) 支持背压BackPressure
 */

public class RxJava2TestView extends YmTestFragment {
    private Observable<String> mObservable = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                for (int i = 0; i < 10; i++) {
                    e.onNext(i+"");
                }
                e.onComplete();
            }
        });

    }

    public void testShowToast() {
        showToastMessage("test");
    }

    /**
     * 一个最简单的RxJava2 实例
     * 创建Observable
     * 创建Observer
     * 建立订阅关系。
     */
    public void testRxJava2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 10; i++) {
                    e.onNext(i);
                }
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            /**
             * 解决订阅 类似月RxJava1.x中的Subscription。
             * @param d
             */
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i(Define.LOG_TAG, "onNext:" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(Define.LOG_TAG, "onComplete");
            }
        });
    }

    public void testRxJava3(){
        mObservable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(Define.LOG_TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.i(Define.LOG_TAG, "onNext:" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(Define.LOG_TAG, "onNext:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(Define.LOG_TAG, "onComplete");
            }
        });
    }

    public void testRxJava4(){
        mObservable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(Define.LOG_TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.i(Define.LOG_TAG, "onNext:" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(Define.LOG_TAG, "onNext:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(Define.LOG_TAG, "onComplete");
            }
        });
    }



    /**
     * 指定线程
     */
    public void testSchedulers(){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(Define.LOG_TAG, integer+" "+Thread.currentThread().getName());
                    }
                });
    }

}
