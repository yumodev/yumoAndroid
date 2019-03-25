package com.yumo.android.test.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yumodev on 17/9/11.
 */

public class BookDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "book_providre.db";
    private static final int DB_VERSION = 1;
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";

    private static final String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "+
            BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";

    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "+
            USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, sex INT)";

    public BookDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
