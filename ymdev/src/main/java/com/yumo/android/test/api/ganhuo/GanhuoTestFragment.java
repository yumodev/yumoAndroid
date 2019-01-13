package com.yumo.android.test.api.ganhuo;

import android.util.Log;

import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yumodev on 17/5/23.
 */

public class GanhuoTestFragment extends YmTestFragment {
    private final String LOG_TAG = "GanhuoTestFragment";
    final String HISTORY_URL = "http://gank.io/api/day/history";

    /**
     * 获取历史数据
     */
    public void testGetHistory(){

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> subscriber) {
                try {
                    String history = YmOkHttpUtil.getBodyString(HISTORY_URL);
                    Log.i(LOG_TAG, history);
                    subscriber.onNext(history);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })  .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToastMessage(s);
                    }
                });


    }

    /**
     * 获取一个分类下的随机数据
     * http://gank.io/api/random/data/Android/20
     */
    public void testCategoryData(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> subscriber) {
                try {
                    String data = YmOkHttpUtil.getBodyString("http://gank.io/api/random/data/Android/20");
                    subscriber.onNext(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {


                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.toString());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToastMessage(s);
                    }
                });

    }

    /**
     * 获取某一天的数据
     */
    public void testGetDay(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> subscriber){
                try {
                    String data = YmOkHttpUtil.getBodyString("http://gank.io/api/random/data/Android/20");
                    subscriber.onNext(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.toString());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToastMessage(s);
                    }
                });
    }
}
