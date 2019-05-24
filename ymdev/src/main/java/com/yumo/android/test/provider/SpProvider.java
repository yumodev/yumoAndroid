package com.yumo.android.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yumo.common.android.YmPrefManager;
import com.yumo.common.log.Log;

import java.io.File;

/**
 * Created by yumodev on 17/9/29.
 */

public class SpProvider extends ContentProvider {
    private static final String LOG_TAG = "SpProvider";
    private static final String AUTHORITY = "com.yumo.android.test.provider.BookContentProvider";
    public static final Uri READ_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/read");
    public static final Uri WRITE_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/write");

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static final int READ_URI_CODE =  0;
    static final int WRITE_URI_CODE = 1;

    static {
        sUriMatcher.addURI(AUTHORITY, "read", READ_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "write", WRITE_URI_CODE);
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String[] path = uri.getPath().split(File.separator);
        if (path.length < 6){
            return null;
        }

        Log.i(LOG_TAG, "type:"+path[1]);
        Log.i(LOG_TAG, "dataType:"+path[2]);
        Log.i(LOG_TAG, "key:"+path[3]);
        Log.i(LOG_TAG, "value:"+path[4]);
        Log.i(LOG_TAG, "fileName:"+path[5]);

        String result;
        String type = path[1];
        String fileName = path[5];
        if (fileName.equals("default")){
            fileName = null;
        }
        if (type.equals("read")){
            result = readSp(path[2], fileName, path[3], path[4]);
        }else{
            result = String.valueOf(writeSp(path[2], fileName, path[3], path[4]));
        }
        return result;
    }

    private String readSp(final String dataType, String fileName, String key, String defaultValue){
        String result = "";
        switch (dataType){
            case "int":{
                int value = YmPrefManager.getInstance().getValue(getContext(), fileName, key, Integer.parseInt(defaultValue));
                result =  String.valueOf(value);
                break;
            }
            case "long":{
                long value = YmPrefManager.getInstance().getValue(getContext(), fileName, key, Long.parseLong(defaultValue));
                result =  String.valueOf(value);
                break;
            }
            case "string":{
                result = YmPrefManager.getInstance().getValue(getContext(), fileName, key, defaultValue);
                break;
            }
            case "boolean":{
                boolean value = YmPrefManager.getInstance().getValue(getContext(), fileName, key, Boolean.parseBoolean(defaultValue));
                result =  String.valueOf(value);
                break;
            }
        }
        return result;
    }

    private boolean writeSp(final String dataType, String fileName, String key, String value){
        boolean result = false;
        switch (dataType){
            case "int":{
                result = YmPrefManager.getInstance().setValue(getContext(), fileName, key, Integer.parseInt(value), false);
                break;
            }
            case "long":{
                result = YmPrefManager.getInstance().setValue(getContext(), fileName, key, Long.parseLong(value), false);
                break;
            }
            case "string":{
                result = YmPrefManager.getInstance().setValue(getContext(), fileName, key, value, false);
                break;
            }
            case "boolean":{
                result = YmPrefManager.getInstance().setValue(getContext(), fileName, key, Boolean.parseBoolean(value), false);
                break;
            }
        }
        return result;
    }



    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
