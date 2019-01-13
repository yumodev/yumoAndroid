package com.yumo.android.test.sys;

import android.content.res.AssetManager;
import android.util.Log;

import com.yumo.common.io.YmIoUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;

/**
 * Created by yumo on 2/14/16.
 */
public class AssetTestView extends YmTestFragment {

    private final String LOG_TAG = "AssetTestView";

    public void testAssetList() {
        AssetManager assets = getActivity().getAssets();
        try {
            String[] dataList = assets.list("");

            for (String file : dataList){
                Log.d(LOG_TAG, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从asset获取一个文件并打印内容
     */
    public void testGetAssetFileToString(){
        String str = "";
        try {
            str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_note.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "testGetAssetFileToString:"+str);
    }

    /**
     * 从asset获取一个文件并打印内容
     */
    public void testGetAssetFileToBytes(){
        String str = "";
        try {
            byte[] data = YmIoUtil.getBytesFromInput(getContext().getAssets().open("test_note.html"));
            if (data != null){
                str = new String(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "testGetAssetFileToString:"+str);
    }
}
