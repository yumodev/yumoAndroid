package com.yumo.android.test.view.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.yumo.android.R;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/11/10.
 */

public class TestInflaterView extends YmTestFragment {
    public void testInflaterView(){
        LayoutInflater layoutInflater = getLayoutInflater();
        layoutInflater.setFactory(new SkinInflaterFactory());
        View view = getLayoutInflater().inflate(R.layout.header_layout, null);
        showTestView(view);
    }


    public class SkinInflaterFactory implements LayoutInflater.Factory {
//
//        @Override
//        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//            Log.i(Log.LIB_LOG, "onCreateView:"+name);
//
//        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            Log.i(Log.LIB_TAG, "onCreateView:"+name);
            View view = null;
            try {
                if (-1 == name.lastIndexOf(".")) {
                    if (name.equals("View") || name.equals("ViewGroup")) {
                        view = getLayoutInflater().createView(name, "android.view.", attrs);
                    } else {
                        view = getLayoutInflater().createView(name, "android.widget.", attrs);
                    }
                } else {
                    if (name.contains(".")) {
                        String checkName = name.substring(name.lastIndexOf("."));
                        String prefix = name.substring(0, name.lastIndexOf("."));
                        view = getLayoutInflater().createView(checkName, prefix, attrs);
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
