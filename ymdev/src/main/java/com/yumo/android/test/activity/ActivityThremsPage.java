/**
 * ActivityThrems.java
 * yumo
 * 2015-3-14
 * TODO
 */
package com.yumo.android.test.activity;

import com.yumo.android.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * yumo
 * 测试activity的窗口样式
 */
public class ActivityThremsPage extends Activity implements View.OnClickListener {
    private final String TAG = "ActivityThremsPage";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //
        Button button = new Button(this);
        button.setId(R.id.btn);
        button.setText("dialog");
        initView();
    }

    /**
     * 初始化界面
     * yumo
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.btn: {
                Log.d(TAG, TAG + "onclick btn");
                finish();
                break;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}

