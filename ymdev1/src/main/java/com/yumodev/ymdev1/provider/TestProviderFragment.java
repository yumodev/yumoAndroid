package com.yumodev.ymdev1.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 10/9/16.
 */

public class TestProviderFragment extends YmTestFragment {
    private final String LOG_TAG = TestProviderFragment.class.getSimpleName();
    public void testProviderDemo(){
        Uri testUri = Uri.parse("content://com.yumo.android.test.provider.MyContentProviderDemo/test");
        getContext().getContentResolver().getType(testUri);
    }


    public void testBookProviderDemo(){
        Uri testUri = Uri.parse("content://com.yumo.android.test.provider.BookContentProvider/test");
        getContext().getContentResolver().getType(testUri);
    }

    public void testInsertBook(){
        Uri testUri = Uri.parse("content://com.yumo.android.test.provider.BookContentProvider/book");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "name");
        getContext().getContentResolver().insert(testUri, contentValues);
    }

    public void testQueryBook(){
        Uri testUri = Uri.parse("content://com.yumo.android.test.provider.BookContentProvider/book");
        Cursor cursor = getContext().getContentResolver().query(testUri, null, null, null, null);
        while (cursor.moveToNext()){
            int  id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Log.i(LOG_TAG, id+" "+name);
        }
    }

    public void testSpProviderRead(){
        Uri testUri = Uri.parse("content://com.yumo.android.test.provider.SpProvider/read/int/test/10/default");
        String type = getContext().getContentResolver().getType(testUri);
        showToastMessage(type);
    }

    public void testSpProviderWrite(){
        Uri testUri = Uri.parse("content://com.yumo.android.test.provider.SpProvider/write/int/test/11/default");
        String type = getContext().getContentResolver().getType(testUri);
        showToastMessage(type);
    }
}
