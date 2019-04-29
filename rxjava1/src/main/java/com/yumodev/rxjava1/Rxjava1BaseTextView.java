package com.yumodev.rxjava1;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;


/**
 * Created by yumodev  on 16/10/17.
 *
 * RxJava基于一种扩展的观察者模式实现。
 * RxJava有下面四部分组成。
 * Observable:可观察者。
 * Observer:观察者。
 * subscribe:订阅
 * 事件。
 *
 * RxJava内置了一个事件Observer的抽象类：Subscriber。
 *
 *
 * 创建Observable .RxJava使用create()方法来创建一个Observable.
 * Observable.create(OnSubscribe)
 * Observable.just(T...)将参数依次发送出来。
 * Observable.from(T[]) from(Iterable<? extends T>)
 *
 *  订阅事件：
 * Observable.subscribe(observer);
 * Observable.unSubscribe();
 * Observable.subscribe(Action1());
 * Observable.subscribe(Action1, Action1, Action0)
 *
 * 线程调度器
 * Schedulers.immediate();直接在当前线程运行。
 * Schedulers.newThread()：总是启用新线程。
 * Schedulers.io()：IO操作。IO()的内部使用一个无数量上限的线程池，可以重用的空闲进程。
 * Schedulers.computation() :计算使用的scheduler。
 * AndroidSchedulers.mainThread()：在Android的主线程运行。
 * subscribeOn()：制定subscribe() 发生时候所在的线程。事件产生线程。
 * observeOn()：制定Subscriber运行的所在线程。事件消费线程。
 *
 * 变换
 * 将事件序列中对象或整个序列进行加工处理，转换成不同的事件或事件序列。
 * map(): 事件对象的直接变换。
 * flatMap():
 */

public class Rxjava1BaseTextView extends YmTestFragment {
    private final String LOG_TAG = "TestRxJavaFragment";
    String str = "a";
    Subscriber mSubscriber = null;
    Observable mObservable = null;

    public void testRxJava(){
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(LOG_TAG, "call threadName:"+Thread.currentThread().getName());
                Log.i(LOG_TAG, "subscriber.isUnsubScribed():"+subscriber.isUnsubscribed());
                for (int i = 0; i < 20; i++){
                    subscriber.onNext(" i = "+i);
                }
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(LOG_TAG, "onCompleted");
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
        };

        observable.subscribe(subscriber);
    }









    /**
     * 实用自定义的Action
     */
    public void testObservableAction(){
        String[] str = {"a", "b", "c"};
        Observable<String> observable = Observable.from(str);
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(LOG_TAG, s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(LOG_TAG, "action1, error throwable");
            }
        }, new Action0(){
            @Override
            public void call() {
                Log.i(LOG_TAG, "action0, call");
            }
        });
    }


    /**
     * IO线程发送数据
     * 主线程处理数据。
     */
    public void testSubscribeOnIo(){
        String[] str = {"a", "b", "c"};
        Observable.from(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscribe());
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

    /**
     * 测试Map转换器的使用
     */
    public void testMapFunc1(){
        String[] str = {"a", "b", "c"};
        Observable.from(str)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.i(LOG_TAG, "func1 threadName:"+Thread.currentThread().getName());
                        return s.toUpperCase();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscribe());
    }


    /**
     * PublishSubject()
     */
    public void testPublishSubject(){
        PublishSubject<String> stringPublishSubject = PublishSubject.create();
        Subscription subscriptionPrint = stringPublishSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(LOG_TAG, "onCompleted:");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(LOG_TAG, "onNext:"+s);
            }
        });

        stringPublishSubject.onNext("hello world");
        stringPublishSubject.onCompleted();
        stringPublishSubject.onNext("hhhh");
    }




    private List<ApplicationInfo> getAppList(Context context){
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        return packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
    }

    /**
     * 打印所有APP
     */
    public void testPrintAppList(){

        Observable.from(getAppList(getContext()))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ApplicationInfo>() {
            @Override
            public void onCompleted() {
                Log.i(LOG_TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(LOG_TAG, "onError:"+e.getMessage());
            }

            @Override
            public void onNext(ApplicationInfo info) {
                Log.i(LOG_TAG, info.toString());
            }
        });
    }


    /**
     * 打印所有APP.直接使用Action1
     */
    public void testPrintAppListAction1(){
        Observable.from(getAppList(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ApplicationInfo>() {
                    @Override
                    public void call(ApplicationInfo info) {
                        Log.i(LOG_TAG, info.toString());
                    }
                });
    }


    /**
     * 打印所有APP.直接使用Map
     */
    public void testPrintAppListMap(){
        Observable.from(getAppList(getContext()))
                .map(new Func1<ApplicationInfo, String>() {

                    @Override
                    public String call(ApplicationInfo info) {
                        return info.packageName;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscribe());
    }


    /**
     * 打印所有APP.首字母为c的APP
     */
    public void testPrintAppListFilter(){

        Observable.from(getAppList(getContext()))
                .filter(new Func1<ApplicationInfo, Boolean>() {
                    @Override
                    public Boolean call(ApplicationInfo info) {
                        return info.packageName.startsWith("c");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ApplicationInfo>() {
                    @Override
                    public void call(ApplicationInfo info) {
                        Log.i(LOG_TAG, info.toString());
                    }
                });
    }

    /**
     * 打印所有APP 获取后三条数据
     */
    public void testPrintAppTakeLast(){

        Observable.from(getAppList(getContext()))
                .takeLast(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ApplicationInfo>() {
                    @Override
                    public void call(ApplicationInfo info) {
                        Log.i(LOG_TAG, info.toString());
                    }
                });
    }


    /**
     * 打印所有APP 获取前3条数据
     */
    public void testPrintAppListTake(){

        Observable.from(getAppList(getContext()))
                .take(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ApplicationInfo>() {
                    @Override
                    public void call(ApplicationInfo info) {
                        Log.i(LOG_TAG, info.toString());
                    }
                });
    }


    public void testCountDown(){
        mSubscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i(LOG_TAG, "计时完成");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(LOG_TAG, "当前计时：" + integer);
            }
        };

        mObservable = RxCountDown.countdown(100);

        mObservable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                Log.i(LOG_TAG, "开始计时");
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mSubscriber);
    }

    public void testUnSubscribe(){
        mSubscriber.unsubscribe();
    }

    public void testUnReSubscribe(){
       mObservable.subscribe(new Action1<Integer>() {
           @Override
           public void call(Integer value) {
               Log.i(LOG_TAG, "当前计时：" + value);
           }
       });
    }
}
