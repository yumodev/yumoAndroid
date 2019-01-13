package com.yumo.android.test.java;

import android.util.Log;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.util.YmSecureUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.io.IOException;

/**
 * Created by yumo on 5/8/16.
 * 涉及到安全性方面的测试
 */
public class TestSecure extends YmTestFragment {
    private final String LOG_TAG = "TestFragment_TestSecure";
    private String mStr = "yumo";

    public void testStringMD5(){
        String hash = YmSecureUtil.getMD5(mStr);
        showToastMessage(hash);
        Log.d(LOG_TAG, "StringMD5:"+hash);
    }

    public void testBytesMD5(){
        String hash = YmSecureUtil.getMD5(mStr.getBytes());
        showToastMessage(hash);
        Log.d(LOG_TAG, "StringMD5:"+hash);
    }

    private String getFileName(){
        return YmAdFileUtil.getAppDataFilesDir(getContext())+ File.separator+"1.txt";
    }

    public void testFileMD5(){
        try {
            YmFileUtil.saveFile(getFileName(), mStr.getBytes());
            String hash = YmSecureUtil.getFileMD5(getFileName());
            Log.d(LOG_TAG, "fileMd5:"+hash);
            showToastMessage(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * md5转guid
     * 582ca5cf-ea5c-450b-b222-9aa52e9d78bf
     * 94635a65-af8e-ff14-5144-0e36b7eb03ad
     */
    public void testMd5ToGuid(){
        String hash = YmSecureUtil.getMD5(mStr.getBytes());
        showToastMessage(hash);
        String uuid = String.format("%s-%s-%s-%s-%s", hash.substring(0, 8), hash.substring(8, 12),
                hash.substring(12,16), hash.substring(16, 20), hash.substring(20,32));
        Log.d(LOG_TAG, "StringMD5:"+hash+"  guid "+ uuid);
    }


    /**
     * <p>123321</p>
     */
    public void testBytesMD51(){
        mStr = "<p>123321</p>"+System.getProperty("line.separator");
        byte[] bytes = mStr.getBytes();
        String hash = YmSecureUtil.getMD5(mStr.getBytes());
        showToastMessage(hash);
        Log.d(LOG_TAG, "StringMD5:"+hash);
    }
}
