package com.yumodev.rxjava2;

import android.app.Dialog;
import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;

import static io.reactivex.Observable.interval;
import static io.reactivex.Observable.just;


/**
 * Created by yumodev on 17/12/11.
 */

public class RxJava2CreateTest extends YmTestFragment {

    public void test(){
        new Thread(() -> showToastMessageOnUiThread("test")).start();
    }

    /**
     * do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
     * 延迟订阅，当有订阅后，才发送数据
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Defer.html
     * http://blog.csdn.net/zhuxuliao/article/details/51542809
     */
    public void testDefer(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        defer(list);
    }

    private void defer(ArrayList<Integer> list){
        Observable defer = Observable.defer((Callable<ObservableSource<?>>) () -> Observable.just(list));
        list.add(11);
        defer.subscribe(integer -> Log.i(Define.LOG_TAG, integer+""));
    }

    /**
     *  convert an object or a set of objects into an Observable that emits that or those objects
     *  依次发送数据
     */
    public void testJust(){
        just(1,2,3,4).subscribe(integer -> Log.i(Define.LOG_TAG, integer+""));
    }

    /**
     * 创建一个不发射任何数据但是正常终止的Observale
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Empty.html
     */
    public void testEmpty(){
        Observable.empty().subscribe(createObserver());
    }

    /**
     * 创建一个不发射数据，也不终止的Observalble
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Empty.html
     */
    public void testNever(){
        Observable.never().subscribe(createObserver());
    }


    /**
     * 将其它种类的对象和数据类型转换为Observable
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/From.html
     */
    public void testFrom(){
        Observable.fromArray(1,2,3).subscribe(createObserver());
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Observable.fromIterable(list).subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * create an Observable that emits a sequence of integers spaced by a given time interval
     * 创建一个按固定时间间隔发射整数序列的Observable
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Interval.html
     * http://reactivex.io/documentation/operators/interval.html
     */
    public void testInterval(){
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            Disposable mDisposable;
            int n = 0;
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                Log.i(Define.LOG_TAG, aLong+"");
                n++;
                if (n == 10){
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(Define.LOG_TAG, "onComplete");
            }
        });
    }

    /**
     * 创建一个发射特定整数序列的Observable
     * create an Observable that emits a particular range of sequential integers
     * http://reactivex.io/documentation/operators/range.html
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Range.html
     */
    public void testRange(){
        Observable.range(1,10).subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 创建一个发射特定数据重复多次的Observable
     * create an Observable that emits a particular item multiple times
     * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Repeat.html
     * http://reactivex.io/documentation/operators/repeat.html
     */
    public void testRepeat(){
        Observable.range(1,5).repeat(10).subscribe(value ->Log.i(Define.LOG_TAG, value+""));
    }


    /**
     * 重复发射数据
     */
    public void testRepeat1(){
        Observable.just(1,2,3)
                .repeat(10)
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    /**
     * 设置重复条件
     */
    public void testRepeatUtnil(){
        Observable.just(1,2,3)
                .repeatUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        return true;
                    }
                })
                .subscribe(value -> Log.i(Define.LOG_TAG, value+""));

    }

    public void testStart(){
    }

    /**
     * create an Observable that emits a particular item after a given delay
     * http://reactivex.io/documentation/operators/timer.html
     */
    public void testTimer(){
        Observable.timer(2, TimeUnit.SECONDS).subscribe(value -> Log.i(Define.LOG_TAG, value+""));
    }

    private Observer createObserver(){
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
