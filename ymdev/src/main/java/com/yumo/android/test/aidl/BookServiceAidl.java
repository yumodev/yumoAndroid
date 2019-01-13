package com.yumo.android.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yumo on 17/8/30.
 * 用户测试的远程book类
 */

public class BookServiceAidl extends Service {
    private final String LOG_TAG = BookServiceAidl.class.getSimpleName();

    private CopyOnWriteArrayList<IBookCallbackListener> mBookCallbackListener = new CopyOnWriteArrayList<>();
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
            mBookCallbackListener.add(listener);
        }

        @Override
        public void unRegisterListener(IBookCallbackListener listener) throws RemoteException {
            mBookCallbackListener.remove(listener);
            Log.i(LOG_TAG, "unRegisterListener:"+mBookCallbackListener.size());
        }
    };

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

    private void addNewBook(){
        Random random = new Random();
        Book book = new Book(random.nextInt(), "bookName:"+random.nextInt());
        mBookList.add(book);
        Log.i(LOG_TAG, "addNewBook:"+(new Gson()).toJson(book)+" "+mBookCallbackListener.size());
        Iterator iterator = mBookCallbackListener.iterator();
        while (iterator.hasNext()){
            IBookCallbackListener listener = (IBookCallbackListener) iterator.next();
            Log.i(LOG_TAG, "onNewBookArrived:");
            try {
                listener.onNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
