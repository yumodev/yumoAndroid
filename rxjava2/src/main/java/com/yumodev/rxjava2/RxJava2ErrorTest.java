package com.yumodev.rxjava2;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yumodev on 17/12/12.
 */

public class RxJava2ErrorTest extends YmTestFragment {
    /**
     * recover from an onError notification by continuing the sequence without error
     * 遇到错误的时候，发射一个特殊的项终止
     */
    public void testOnErrorReturn(){
        Observable.error(new Throwable("Test Error"))
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) throws Exception {
                        return "onErrorReturn";
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(Define.LOG_TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(Define.LOG_TAG, "onNext:"+String.valueOf(o));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Define.LOG_TAG, "onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(Define.LOG_TAG, "onComplete");
                    }
                });
    }


    public void testOnErrorReturnItem(){
        Observable.error(new Throwable("Test Error"))
                .onErrorReturnItem("错误")
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(Define.LOG_TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(Define.LOG_TAG, "onNext:"+String.valueOf(o));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Define.LOG_TAG, "onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(Define.LOG_TAG, "onComplete");
                    }
                });
    }

    public void testOnErrorResumeNext(){
        Observable.error(new Throwable("Test Error"))
                .onErrorResumeNext(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        return Observable.just(1,2,4);
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(Define.LOG_TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(Define.LOG_TAG, "onNext:"+String.valueOf(o));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Define.LOG_TAG, "onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(Define.LOG_TAG, "onComplete");
                    }
                });
    }

    public void testOnExceptionResumeNext(){
        Observable.error(new Throwable("Test Error"))
                .onExceptionResumeNext(Observable.just(1,2,3,4))
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(Define.LOG_TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(Define.LOG_TAG, "onNext:"+String.valueOf(o));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Define.LOG_TAG, "onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(Define.LOG_TAG, "onComplete");
                    }
                });
    }


    /**
     * 如果原始Observable遇到错误，重新订阅它期望它能正常终止
     */
    public void testRetry() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("Error"));
            }
        })
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(Define.LOG_TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(Define.LOG_TAG, "onNext:"+String.valueOf(o));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Define.LOG_TAG, "onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(Define.LOG_TAG, "onComplete");
                    }
                });
    }

}
