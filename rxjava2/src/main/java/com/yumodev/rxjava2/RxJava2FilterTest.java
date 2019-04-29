package com.yumodev.rxjava2;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yumodev on 17/12/11.
 * https://maxwell-nc.github.io/android/rxjava2-3.html
 * 过滤操作符
 *
 * [RxJava2过滤操作符篇](http://www.jianshu.com/p/2200486b5d9a)
 */

public class RxJava2FilterTest extends YmTestFragment {

    /**
     * only emit an item from an Observable if a particular time-span has passed without it emitting another item,
     * 仅在过了一段指定的时间还没发射数据时才发射一个数据
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Debounce.html
     *
     * http://www.jianshu.com/p/ee1f0d21a856
     */
    public void testDebounce(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(1000);
                e.onNext(2);
                Thread.sleep(4000);
                e.onNext(3);
                Thread.sleep(1000);
                e.onNext(4);
                Thread.sleep(4000);
                e.onNext(5);
                Thread.sleep(1000);
                e.onComplete();
            }
        })
                .debounce(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(Define.LOG_TAG, value+"");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * Distinct的过滤规则是：只允许还没有发射过的数据项通过。
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Distinct.html
     */
    public void testDistinct(){
        Observable.just(1,2,1,2,1,2)
                .distinct()
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    public void testDistinctFunc(){
        Observable.just(1,2,4,8,16,3)
                .distinct(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return  integer % 2;
                    }
                })
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 它只判定一个数据和它的直接前驱是否是不同的。
     */
    public void testDistinceUntilChanged(){
        Observable.just(1,1,2,2,1,2)
                .distinctUntilChanged()
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 只发射第n项数据
     *
     */
    public void testElemmentAt(){
        Observable.range(0,9)
                .elementAt(9)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 只发射第n项数据
     *
     */
    public void testFirstElement(){
        Observable.range(0,9)
                .firstElement()
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 只发射第一项（或者满足某个条件的第一项）数据
     *
     */
    public void testFirst(){
        Observable.range(0,9)
                .first(1)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 只发射第n项数据
     *
     */
    public void testLast(){
        Observable.range(0,9)
                .lastElement()
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 只发射前面的N项数据
     *
     */
    public void testTake(){
        Observable.range(0,9)
                .take(3)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 发射Observable发射的最后N项数据
     */
    public void testTakeLast(){
        Observable.range(0,9)
                .takeLast(3)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 抑制Observable发射的前N项数据
     *
     */
    public void testSkip(){
        Observable.range(0,9)
                .skip(3)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 抑制Observable发射的后N项数据
     *
     */
    public void testSkipLast(){
        Observable.range(0,9)
                .skipLast(3)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    public void testFilter(){
        Observable.range(0,9)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 3;
                    }
                })
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }


    /**
     * 定期发射Observable最近发射的数据项
     */
    public void testSample(){
        Observable.range(0,10000)
                .sample(10, TimeUnit.MILLISECONDS)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 不发射任何数据，只发射Observable的终止通知
     */
    public void testIngoreElements(){
        Observable.range(0,9)
                .ignoreElements()
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(Define.LOG_TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Define.LOG_TAG, "onError");
                    }
                });
    }


    private Observer createObserver() {
        return new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.i(Define.LOG_TAG, o.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(Define.LOG_TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(Define.LOG_TAG, "onComplete");
            }
        };
    }
}
