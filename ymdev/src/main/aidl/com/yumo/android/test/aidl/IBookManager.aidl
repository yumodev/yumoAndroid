package com.yumo.android.test.aidl;

import com.yumo.android.test.aidl.Book;
import com.yumo.android.test.aidl.IBookCallbackListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
//     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IBookCallbackListener listener);
    void unRegisterListener(IBookCallbackListener listener);
}


