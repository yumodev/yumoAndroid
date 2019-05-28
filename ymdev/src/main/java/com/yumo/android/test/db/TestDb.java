package com.yumo.android.test.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/4/4.
 */

public class TestDb extends YmTestFragment {

    public void testOpenDb(){
        SQLiteOpenHelper db = new SQLiteOpenHelper(getContext(), "1.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                showToastMessage("create db");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        db.getWritableDatabase();
        showToastMessage(db.getDatabaseName());
    }
}
