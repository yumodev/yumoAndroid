package com.yumo.android.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yumo.android.test.db.BookDbOpenHelper;

/**
 * Created by yumodev on 17/9/11.
 * 一个简单的BOOK类来测试Provider的使用
 */

public class BookContentProvider extends ContentProvider {
    private final String LOG_TAG = BookContentProvider.class.getSimpleName();

    private static final String AUTHORITY = "com.yumo.android.test.provider.BookContentProvider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/user");

    public static final String BOOK_FIELD_NAME = "name";
    public static final String BOOK_FIELD_ID = "_id";



    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private SQLiteDatabase mDb = null;

    @Override
    public boolean onCreate() {
        Log.i(LOG_TAG, "onCreate(): "+Thread.currentThread().getName());
        mDb = new BookDbOpenHelper(getContext()).getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(LOG_TAG, "query:"+uri.toString());
        String tableName = getTableName(uri);
        if (tableName != null){
            return mDb.query(tableName, projection, selection, selectionArgs,null, sortOrder, null);
        }else{
            throw new IllegalArgumentException("Unsupported uri:"+uri.toString());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(LOG_TAG, "getType:"+uri.toString());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i(LOG_TAG, "insert:"+uri.toString());
        String tableName = getTableName(uri);
        if (tableName != null){
            mDb.insert(tableName, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
        }else{
            throw new IllegalArgumentException("Unsupported uri:"+uri.toString());
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(LOG_TAG, "insert:"+uri.toString());
        String tableName = getTableName(uri);
        if (tableName != null){
            int count = mDb.delete(tableName, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }else{
            throw new IllegalArgumentException("Unsupported uri:"+uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(LOG_TAG, "update:"+uri.toString());
        String tableName = getTableName(uri);
        if (tableName != null){
            int count = mDb.update(tableName, values, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }else{
            throw new IllegalArgumentException("Unsupported uri:"+uri.toString());
        }
    }


    private String getTableName(Uri uri){
        String tableName = "";
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:{
                tableName = BookDbOpenHelper.BOOK_TABLE_NAME;
                break;
            }
            case USER_URI_CODE:{
                tableName = BookDbOpenHelper.USER_TABLE_NAME;
                break;
            }
        }
        return tableName;
    }
}
