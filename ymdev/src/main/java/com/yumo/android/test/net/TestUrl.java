package com.yumo.android.test.net;

import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

/**
 * Created by yumo on 7/15/16.
 * URL,URI 等等相关的测试方法
 */
public class TestUrl extends YmTestFragment {
    private final String LOG_TAG = "TestUrl";

    public void testDecodeUrl(){
        String encode = "%E6%88%91%E7%88%B1%E4%BD%A0%2B%2B";

        String decode = null;
        try {
            decode = URLDecoder.decode(encode, "utf-8");
            Log.d(LOG_TAG, "decode:"+decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void testDecodeUri(){
        String encode = "http://%E6%88%91%E7%88%B1%E4%BD%A0++";

        String decode = null;
        try {
            URI uri = new URI(encode);
            //decode = uri.decode(decode);
            Log.d(LOG_TAG, "decode:"+decode);
        }  catch (URISyntaxException e){
            e.printStackTrace();
        }
    }
}
