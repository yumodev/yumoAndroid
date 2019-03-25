package com.yumo.android.module.user;

/**
 * Created by yumodev on 17/1/9.
 */

public class UserManaer {
    public final String LOG_TAG = "UserManaer";
    private static UserManaer mInstance = null;
    private UserManaer(){}

    public UserManaer getInstance(){
        if (mInstance == null){
            synchronized (UserManaer.this){
                mInstance = new UserManaer();
            }
        }
        return mInstance;
    }
}
