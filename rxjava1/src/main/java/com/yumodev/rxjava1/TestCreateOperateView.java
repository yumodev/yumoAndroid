package com.yumodev.rxjava1;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;

import static com.yumodev.rxjava1.Define.LOG_TAG;

/**
 * Created by yumodev on 17/12/5.
 *
 * just:利用just依次将数据发送出去
 * from:利用from将一个数组数据依次发送出去,和just一样
 * defer:直到有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
 * timer:操作符创建一个在给定的时间段之后返回一个特殊值的Observable。
 * interval: 创建一个按固定时间间隔发射整数序列的Observable
 * range:操作符发射一个范围内的有序整数序列，你可以指定范围的起始和长度
 * repeat:创建一个发射特定数据重复多次的Observable
 */

public class TestCreateOperateView extends YmTestFragment {

    String mData = "a";

    /**
     * 利用just依次将数据发送出去
     */
    public void testObservablejust(){
        Observable
                .just("a", "b", "c", "d")
                .subscribe(getSubscribe());
    }

    /**
     * 利用from将一个数组数据依次发送出去,和just一样
     */
    public void testObservableFrom(){
        String[] str = {"a", "b", "c"};
        Observable
                .from(str)
                .subscribe(getSubscribe());
    }

    /**
     * Range操作符发射一个范围内的有序整数序列，你可以指定范围的起始和长度。
     */
    public void testObservableRange(){

        Observable
                .range(0, 10)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer value) {
                        Log.i(LOG_TAG, value+"");
                    }
                });
    }

    /**
     * repeat:创建一个发射特定数据重复多次的Observable
     */
    public void testObserableRepeat(){
        Observable
                .range(0, 10)
                .repeat(5)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer value) {
                        Log.i(LOG_TAG, value+"");
                    }
                });
    }

    /**
     * Timer操作符创建一个在给定的时间段之后返回一个特殊值的Observable。
     */
    public void testTimer(){
        Observable.just(null)
                .timer(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i(LOG_TAG, "testTimer");
                    }
                });
    }

    /**
     * Delay操作符创建一个在给定的时间段之后返回一个特殊值的Observable。
     */
    public void testDeleay(){
        Observable.just(null)
                .delay(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i(LOG_TAG, "testDelay");
                    }
                });
    }

    /**
     * interval: 创建一个按固定时间间隔发射整数序列的Observable
     */
    public void testInterval(){
        Observable.range(10,10)
                .interval(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i(LOG_TAG, "testInterval:"+aLong);
                    }
                });
    }


    /**
     * Empty 创建一个不发射任何数据但是正常终止的Observable
     */
    public void testEmpty(){
        Observable.<String>empty().subscribe(getSubscribe());
    }

    /**
     * never 创建一个不发射数据也不终止的Observable
     */
    public void testNever(){
        Observable.<String>never().subscribe(getSubscribe());
    }


    /**
     * throw 创建一个不发射数据以一个错误终止的Observable
     */
    public void testThrowable(){
        Observable.<String>error(new Throwable()).subscribe(getSubscribe());
    }
    /**
     * 直到有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
     */
    public void testDefer(){
        Observable observable = Observable.just(mData).defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(mData);
            }
        });

        mData = "c";
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(LOG_TAG, s);
            }
        });
    }


    private Subscriber<String> getSubscribe(){
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(LOG_TAG, "onCompleted threadName:"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(LOG_TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i(LOG_TAG, "onNext:"+s);
                Log.i(LOG_TAG, "onNext threadName:"+Thread.currentThread().getName());
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.i(LOG_TAG, "onStart threadName:"+Thread.currentThread().getName());
            }
        };
        return subscriber;
    }


}
