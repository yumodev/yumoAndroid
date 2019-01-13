package com.yumo.android.test.aidl;

import com.yumo.android.test.aidl.Book;

interface IBookCallbackListener {
   void onNewBookArrived(in Book book);
}


