/**
 * WifiDemoActivity.java
 * yumo
 * 2014-12-30
 * 一个模拟天翼wifi管理类。显示wifi列表。模拟wifi，打开关闭wifi，输入wifi的账号和密码进行登陆。
 */
package com.yumo.android.test.net.wifi;

import com.yumo.android.R;
import com.yumo.common.net.YmWifiUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * yumo
 *
 */
public class WifiManagerActivity extends Activity implements View.OnClickListener {
    private final String TAG = WifiManagerActivity.class.getSimpleName() + "  ";

    private ListView mList = null;
    private WifiManagerAdapter mAdapter = null;

    private YmWifiUtils mWifiUtils = null;
    private IntentFilter mIntentFilter = null;
    private BroadcastReceiver mBroadcastReceiver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wifi_manager_page);
        initView();
    }

    /**
     * 初始化界面
     * yumo
     * @return
     * boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("WLAN");

        mList = (ListView) findViewById(R.id.list);
        mAdapter = new WifiManagerAdapter(this);
        mList.setAdapter(mAdapter);

        mWifiUtils = new YmWifiUtils(this);

        mWifiUtils.getWifiManager().startScan();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleEvent(intent);
            }
        };

        registerReceiver(mBroadcastReceiver, mIntentFilter);

        return true;
    }

    /**
     * 处理wifi的状态。
     * yumo
     * @param intent
     * void
     * 2014-12-30
     */
    private void handleEvent(Intent intent) {
        do {
            if (intent == null) break;
            String strAction = intent.getAction();
            if (strAction == null) break;
            Log.d(TAG, intent.getAction());

            if (strAction.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                //扫描结束，更新扫描结果。
                mAdapter.setScanResultList(mWifiUtils.getWifiResult(), mWifiUtils);
                mAdapter.notifyDataSetChanged();
            }

        } while (false);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, TAG + "onclick backimg");
                finish();
                break;
            }
        }
    }
}
