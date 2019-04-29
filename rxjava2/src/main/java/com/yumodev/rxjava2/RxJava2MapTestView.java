package com.yumodev.rxjava2;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;


/**
 * Created by yumodev on 17/12/10.
 *
 * 转换操作符的使用
 * http://blog.csdn.net/job_hesc/article/details/46495281
 */

public class RxJava2MapTestView extends YmTestFragment {

    /**
     * 对Observable发射的每一项数据应用一个函数，执行变换操作
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Map.html
     */
    public void testMap(){
        Observable.fromArray("Hello World".split(" "))
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s.toLowerCase();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(Define.LOG_TAG, "map: "+s);
                    }
                });
    }

    /**
     * FlatMap将一个发射数据的Observable变换为多个Observables，然后将它们发射的数据合并后放进一个单独的Observable
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/FlatMap.html
     */
    public void testFlatMap(){
        Observable.fromArray("Hello World".split(" "))
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource apply(String s) throws Exception {
                        return Observable.just(s.toUpperCase(), s.toLowerCase(), s+"1234").delay(10, TimeUnit.SECONDS);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(Define.LOG_TAG, "flatMap: "+s);
                    }
                });
    }


    public void testConcatMap(){
        Observable.fromArray("Hello World".split(" "))
                .concatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource apply(String s) throws Exception {
                        return Observable.just(s.toUpperCase(), s.toLowerCase(), s+"1234").delay(10, TimeUnit.SECONDS);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(Define.LOG_TAG, "concatMap: "+s);
                    }
                });
    }

    /**
     * 定期收集Observable的数据放进一个数据包裹，然后发射这些数据包裹，而不是一次发射一个值。
     * periodically gather items emitted by an Observable into bundles and emit these bundles rather than emitting the items one at a time
     * http://reactivex.io/documentation/operators/buffer.html
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Buffer.html
     */
    public void testBuffer(){
        Observable.range(1, 100).buffer(10).subscribe(integers -> Log.i(Define.LOG_TAG, integers.toString()));
    }

    /**
     * 将一个Observable分拆为一些Observables集合，它们中的每一个发射原始Observable的一个子序列
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/GroupBy.html
     */
    public void testGroupBy(){
        Observable.range(1,9).groupBy(new Function<Integer, Integer>(){

            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer % 3;
            }
        }).subscribe(new Consumer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void accept(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) throws Exception {
                integerIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(Define.LOG_TAG, integerIntegerGroupedObservable.getKey()+" value:"+integer);
                    }
                });
            }
        });
    }

    /**
     * 连续地对数据序列的每一项应用一个函数，然后连续发射结果
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Scan.html
     */
    public void testScan(){
        Observable.range(1, 9)
                .scan((value1, value2) -> value1 + value2)
                .subscribe(value -> Log.i(Define.LOG_TAG, "scan:"+value));
    }

    /**
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Window.html
     */
    public void testWindow(){
        Observable.range(1,9)
                .window(2)
                .subscribe(new Consumer<Observable<Integer>>() {
                    @Override
                    public void accept(Observable<Integer> integerObservable) throws Exception {
                        Log.i(Define.LOG_TAG, "window:");
                        integerObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.i(Define.LOG_TAG, integer+"");
                            }
                        });
                    }
                });
    }
}
