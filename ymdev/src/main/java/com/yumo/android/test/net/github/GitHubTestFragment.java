package com.yumo.android.test.net.github;

import android.util.Log;

import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;

/**
 * Created by yumodev on 17/3/8.
 */

public class GitHubTestFragment extends YmTestFragment {

    public static final String LOG_TAG = "GithubTestFragment";


    private String getLoginName(){
        return "yumodev";
    }

    public void testGetUserInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.github.com/users/"+getLoginName();
                String result = "";
                try {
                    result = YmOkHttpUtil.getBodyString(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (result != null){
                    Log.i(LOG_TAG, result);
                    showToastMessageOnUiThread(result);
                }
            }
        }).start();
    }

    public void testGetFollers(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.github.com/users/"+getLoginName()+"/followers";
                String result = "";
                try {
                    result = YmOkHttpUtil.getBodyString(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (result != null){
                    Log.i(LOG_TAG, result);
                    showToastMessageOnUiThread(result);
                }
            }
        }).start();
    }

}
