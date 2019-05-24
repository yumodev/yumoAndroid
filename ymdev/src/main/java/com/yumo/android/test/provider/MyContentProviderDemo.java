package com.yumo.android.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by yumodev on 10/9/16.
 */

public class MyContentProviderDemo extends ContentProvider{
    private final String LOG_TAG = "MyContentProviderDemo";

    @Override
    public boolean onCreate() {
        Log.i(LOG_TAG, "onCreate()");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(LOG_TAG, "query:"+uri.toString());
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.i(LOG_TAG, "getType:"+uri.toString());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(LOG_TAG, "insert:"+uri+"  values:"+values.toString());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(LOG_TAG, "delete:"+uri.toString());
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(LOG_TAG, "update:"+uri.toString());
        return 0;
    }
}
