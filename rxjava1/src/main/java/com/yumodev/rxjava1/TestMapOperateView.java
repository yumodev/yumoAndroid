package com.yumodev.rxjava1;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by yumodev on 17/12/5.
 * 变换操作符，对Observable发射的数据按照一定的规则做一些变换操作。
 */

public class TestMapOperateView extends YmTestFragment {

    /**
     * 测试Map转换器的使用
     */
    public void testMapFunc1(){
      Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 20; i++){
                    subscriber.onNext(String.valueOf(i));
                }
                subscriber.onCompleted();
            }
        })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.valueOf(s);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("map", integer+"");
                    }
                });
    }

    /**
     * Map操作符对原始的Observable发射的每一项数据应用一个你选择的函数，然后返回一个发射这些结果的Observable
     */
    public void testMap(){
        Observable.range(0,10)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer value) {
                        return value * 10;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer value) {
                Log.i(Define.LOG_TAG, value+"");
            }
        });
    }

    /**
     * cast操作符将原始的Observable发射的每一项数据都强制转换为一个指定的类型，然后在发射数据
     * 是Map的一个特殊版本
     *
     */
    public void testCast(){
        // 将整形数据转换为字符串数据。
        Observable.range(0, 10)
                .cast(Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer s) {
                        Log.i(Define.LOG_TAG, s+"");
                    }
                });
    }

    /**
     * FlatMap 将一个发射数据的Observable变换为多个Observables，然后将他们发射的数据合并后放进一个单独的Observable中
     */
    public void testFlatMap(){
        Observable.range(0, 10)
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        return Observable.just(integer * 100);
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer value) {
                Log.i(Define.LOG_TAG, value+"");
            }
        });
    }
}
