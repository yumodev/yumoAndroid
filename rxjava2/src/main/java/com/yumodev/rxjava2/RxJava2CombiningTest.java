package com.yumodev.rxjava2;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;

import static io.reactivex.Observable.combineLatest;

/**
 * Created by yumodev on 17/12/12.
 * 组合操作符
 * [Android RxJava：组合 / 合并操作符 详细教程](http://www.jianshu.com/p/c2a7c03da16d)
 * [组合操作符](https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Combining-Observables.html)
 */

public class RxJava2CombiningTest extends YmTestFragment {

    /**
     * 组合多个被观察者，按照顺序发送
     * ConcatArray
     */
    public void testConcat(){
        Observable.concat(Observable.range(0,3),
                Observable.range(3,6))
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 合并多个被观察者，按照时间线发送数据
     * 合并多个Observables的发射物
     * 使用Merge操作符你可以将多个Observables的输出合并，就好像它们是一个单个的Observable一样。
     Merge可能会让合并的Observables发射的数据交错（有一个类似的操作符Concat不会让数据交错，它会按顺序一个接着一个发射多个Observables的发射物）。
     https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Merge.html
     */
    public void testMerge(){
        Observable.merge(Observable.intervalRange(0,2, 0 , 1, TimeUnit.SECONDS),
                Observable.intervalRange(3,4, 0 , 1, TimeUnit.SECONDS))
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 前面的事件发生错误后，还可以继续发送发射后面的事件
     */
    public void testMergeDelayError(){
        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 5; i++){
                            e.onNext(i);
                            if (i == 2){
                                e.onError(new NullPointerException());
                            }
                        }
                    }
                }),
                Observable.range(5, 5))
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(Define.LOG_TAG, integer+"");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(Define.LOG_TAG, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * zip类似。zip是每个事件进行匹配，combineLatest按照时间进行匹配
     */
    public void testCombineLatest(){
        combineLatest(Observable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS),
                Observable.intervalRange(3, 3, 1, 1, TimeUnit.SECONDS)
                , (integer, integer2) -> integer+" "+integer2)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));


    }

    /**
     * 按顺序对Observable发射的每项数据应用一个函数并发射最终的值
     * 将事件合并成一个发送
     */
    public void testReduce(){
        Observable.range(1, 3)
                .reduce((value1, value2) -> value1+value2)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    public void testCollect(){
        Observable.range(1,3)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<Integer>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> list, Integer integer) throws Exception {
                        list.add(integer);
                    }
                }).subscribe(list -> Log.i(Define.LOG_TAG, list.toString()));
    }

    /**
     * 在数据序列的开头插入一条指定的项
     */
    public void testStartWidth(){
        Observable.range(0,3)
                .startWith(-1)
                .startWithArray(-2, -3)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }


}
