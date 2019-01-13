package com.yumo.android.test.activity.fragment;

import android.util.Log;
import android.view.ViewGroup;

import com.yumo.demo.view.YmTestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 16/12/12.
 */

public class TestYmFragment extends YmTestFragment {

    private final String LOG_TAG = "TestYmFragment";
    /**
     * 遍历打印View
     */
    public void testPrintView(){
        ViewGroup view = (ViewGroup)getView();
        List<ViewGroup> listView = new ArrayList<>();
        listView.add(view);
        while (listView.size() > 0){
            ViewGroup vg = listView.remove(0);
            for (int i = 0; i < vg.getChildCount(); i++){
                if (vg.getChildAt(i) instanceof ViewGroup){
                    Log.i(LOG_TAG, "ViewGroup"+vg.getChildAt(i).getClass().getName());
                    listView.add((ViewGroup) vg.getChildAt(i));
                }else{
                    Log.i(LOG_TAG, "ViewGroup:"+vg.getChildAt(i).getClass().getName());
                }
            }
        }
    }

}
