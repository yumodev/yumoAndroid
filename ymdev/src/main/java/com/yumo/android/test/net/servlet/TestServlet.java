package com.yumo.android.test.net.servlet;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yumodev on 8/1/16.
 */
public class TestServlet extends YmTestFragment {
    private final String LOG_TAG = "TestServelt";
    String mUrl = "http://10.0.5.33:8080";


    public void testHelloServlet(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                for (int n =0; n < 10; n++){
                    String url = mUrl + "";

                    Request request = new Request.Builder().url(url).build();

                    try {
                        Response response = client.newCall(request).execute();
                        if (response != null && response.isSuccessful()){
                            Log.d(LOG_TAG, "getHello:" + response.body().string());
                            showToastMessageOnUiThread(response.body().toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
