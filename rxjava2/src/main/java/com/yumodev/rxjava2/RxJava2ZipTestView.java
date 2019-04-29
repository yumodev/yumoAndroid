package com.yumodev.rxjava2;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yumodev on 17/12/10.
 * 测试zip的操作符的使用
 *
 * * [给初学者的RxJava2.0教程(四)](http://www.jianshu.com/p/bb58571cdb64)
 */

public class RxJava2ZipTestView extends YmTestFragment {

    public void testZip(){
        Observable observableA = Observable.just(1,2,3,4).subscribeOn(Schedulers.io());
        Observable observableB = Observable.just("A", "B","c");

        Observable.zip(observableA, observableB, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer value1, String value2) throws Exception {
                return value1+" "+value2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                Log.i(Define.LOG_TAG, "testZip:"+o);
            }
        });
    }
}
