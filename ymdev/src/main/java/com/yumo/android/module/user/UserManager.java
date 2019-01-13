package com.yumo.android.module.user;

/**
 * Created by yumodev on 17/1/9.
 */

public class UserManager {
    public final String LOG_TAG = "UserManager";
    private static UserManager mInstance = null;
    private UserManager(){}

    public UserManager getInstance(){
        if (mInstance == null){
            synchronized (UserManager.this){
                mInstance = new UserManager();
            }
        }
        return mInstance;
    }
}
