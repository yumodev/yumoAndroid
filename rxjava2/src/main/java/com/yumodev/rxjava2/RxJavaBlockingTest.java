package com.yumodev.rxjava2;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by yumodev on 17/12/13.
 */

@TargetApi(Build.VERSION_CODES.N)
public class RxJavaBlockingTest extends YmTestFragment {

    public void testToList(){
        Observable.just(1, 2, 3)
                .toList()
                .blockingGet()
                .forEach(value -> Log.i(Define.LOG_TAG, value+""));
    }

    public void testToSortedList(){
        Observable.just(3,2,1)
                .toSortedList()
                .blockingGet()
                .forEach(value -> Log.i(Define.LOG_TAG, value+""));
    }

    public void testToMap(){
        Map<Integer, Integer> map = Observable.just(1, 2, 3)
                .toMap(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer * 2;
                    }
                }).blockingGet();


               Log.i(Define.LOG_TAG, map.toString());
    }

    public void testToMultiMap(){
        Map<Integer, Collection<Integer>> map = Observable.just(1, 2, 3)
                .toMultimap(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer + integer;
                    }
                }).blockingGet();


        Log.i(Define.LOG_TAG, map.toString());
    }
}
