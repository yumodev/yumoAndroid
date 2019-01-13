/**
 * ActivityOrienationPage.java
 * yumo
 * 2015-3-14
 * TODO
 */
package com.yumo.android.test.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.yumo.android.R;

/**
 * yumo
 * Activity屏幕切换的测试
 */
public class ActivityOrienationPage extends Activity implements View.OnClickListener {
    private final String TAG = "ActivityOrientation";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //
        Button button = new Button(this);
        button.setId(R.id.btn);
        button.setText("switch");
        setContentView(button);
        initView();
    }

    /**
     * TODO 初始化界面
     * yumo
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        return true;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.btn: {
                Log.d(TAG, "onclick btn");
                finish();
                break;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfiguratinChanged");
    }

}
