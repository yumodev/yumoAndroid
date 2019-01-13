package com.yumo.android.test.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.google.gson.Gson;
import com.yumo.android.test.aidl.Book;
import com.yumo.android.test.aidl.BookServiceAidl;
import com.yumo.android.test.aidl.IBookCallbackListener;
import com.yumo.android.test.aidl.IBookManager;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.util.List;
import java.util.Random;

/**
 * Created by yumodev on 17/8/30.
 * 测试IBookManager的方法
 */

public class TestBookServeceView extends YmTestFragment {
    private final String LOG_TAG = "TestBookServiceView";
    private ServiceConnection mServiceConnection = null;
    private IBookManager mBookManager = null;


    private IBookCallbackListener mBookCallbackListener = new IBookCallbackListener.Stub(){
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Log.i(LOG_TAG, "onNewBookArrived:"+(new Gson()).toJson(book));
        }
    };

    @Override
    public void onDestroy() {
        if (mServiceConnection != null){
            getActivity().unbindService(mServiceConnection);
        }
        super.onDestroy();
    }

    public void testBinder(){
        if (mServiceConnection == null){
            mServiceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Log.i(LOG_TAG, "onServiceConnected");
                    mBookManager = IBookManager.Stub.asInterface(service);
                    try {
                        mBookManager.registerListener(mBookCallbackListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.i(LOG_TAG, "onServiceDisconnected");
                    try {
                        mBookManager.unRegisterListener(mBookCallbackListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            };

            Intent intent = new Intent(getActivity(), BookServiceAidl.class);
            getActivity().bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
        }else{
            showToastMessage("已绑定成功");
        }
    }

    public void testPrintBookList() throws RemoteException {
        if (mBookManager == null){
            testBinder();
        }

        List<Book> list = mBookManager.getBookList();
        showToastMessage(new Gson().toJson(list));
    }


    public void testAddBook() throws RemoteException {
        if (mBookManager == null){
            testBinder();
        }

        Random random = new Random();
        Book book = new Book(random.nextInt(), random.nextInt()+"Name");
        mBookManager.addBook(book);
    }

}
