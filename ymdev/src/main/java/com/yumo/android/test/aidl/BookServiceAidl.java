package com.yumo.android.test.aidl;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.yumo.android.common.base.BaseService;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yumo on 17/8/30.
 * 用户测试的远程book类
 */

public class BookServiceAidl extends BaseService {
    private final String LOG_TAG = BookServiceAidl.class.getSimpleName();

    private RemoteCallbackList<IBookCallbackListener> mListenerList = new RemoteCallbackList<>();
    /**
     * 支持并发读写。
     */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private AtomicBoolean mIsDestroy = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.i(LOG_TAG, "getBookList");
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.i(LOG_TAG, "addBook");
            mBookList.add(book);
        }

        @Override
        public void registerListener(IBookCallbackListener listener) throws RemoteException {
            Log.i(LOG_TAG, "registerListener");
           mListenerList.register(listener);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unRegisterListener(IBookCallbackListener listener) throws RemoteException {
           mListenerList.unregister(listener);
            Log.i(LOG_TAG, "unRegisterListener:"+mListenerList.getRegisteredCallbackCount());
        }
    };

    @Override
    protected String getTag() {
        return LOG_TAG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        mIsDestroy.set(true);
        bookThread();
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "onDestroy");
        mIsDestroy.set(false);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(LOG_TAG, "onRebind");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private void bookThread(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                while (mIsDestroy.get()){
                    addNewBook();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addNewBook(){
        Random random = new Random();
        Book book = new Book(random.nextInt(), "bookName:"+random.nextInt());
        mBookList.add(book);
        int listenNum = mListenerList.getRegisteredCallbackCount();
        Log.i(LOG_TAG, "addNewBook:"+(new Gson()).toJson(book)+" "+listenNum);
        for (int i = 0 ; i < listenNum; i++){
            IBookCallbackListener listener = mListenerList.getBroadcastItem(i);
            Log.i(LOG_TAG, "onNewBookArrived:");
            try {
                listener.onNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
