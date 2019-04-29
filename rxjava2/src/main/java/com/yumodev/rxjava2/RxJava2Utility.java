package com.yumodev.rxjava2;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;

/**
 * Created by yumodev on 17/12/12.
 * 辅助操作符
 */

public class RxJava2Utility extends YmTestFragment {

    /**
     * 延时访问
     */
    public void testDelay(){
        Observable.range(0,3)
                .delay(10, TimeUnit.SECONDS)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 延时访问
     */
    public void testdelaySubscription(){
        Observable.range(0,3)
                .delaySubscription(10, TimeUnit.SECONDS)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""),
                throwable -> Log.i(Define.LOG_TAG, throwable.getMessage()),
                        ()-> Log.i(Define.LOG_TAG, "onComplete"),
                disposable -> Log.i(Define.LOG_TAG, "disposable"));
    }


    public void testDo(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        })
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        Log.i(Define.LOG_TAG, "doOnEach:"+integerNotification.getValue());
                    }
                })
                .doOnNext(value -> Log.i(Define.LOG_TAG, "doOnNext:"+value))
                .doAfterNext(value -> Log.i(Define.LOG_TAG, "doAfterNext:"+value))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.i(Define.LOG_TAG, "doOnSubScribe");
                    }
                })
                .doOnDispose(()-> Log.i(Define.LOG_TAG, "doOnDispose"))
                .doOnTerminate(()-> Log.i(Define.LOG_TAG, "doOnTerminate"))
                .doAfterTerminate(()-> Log.i(Define.LOG_TAG, "doAfterTerminate"))
                .doOnComplete(()-> Log.i(Define.LOG_TAG, "doOnComplete"))
                .doOnError( value -> Log.i(Define.LOG_TAG, value.getMessage()))
                .doFinally(()-> Log.i(Define.LOG_TAG, "doFinally"))
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(Define.LOG_TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(Define.LOG_TAG, "onNext:"+integer);
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
