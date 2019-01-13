/**
 * WifiManagerAdapter.java
 * yumo
 * 2014-12-30
 * TODO
 */
package com.yumo.android.test.net.wifi;

import java.util.List;

import com.yumo.android.R;
import com.yumo.common.net.YmWifiUtils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * yumo
 */
public class WifiManagerAdapter extends BaseAdapter {

    private final String TAG = WifiManagerAdapter.class.getSimpleName();
//    private static final int[] STATE_SECURED = {R.attr.state_encrypted};
//    private static final int[] STATE_NONE = {};    

    private LayoutInflater mInflater;
    private List<ScanResult> mScanResultList = null;
    private YmWifiUtils mWifiUtils = null;

    public WifiManagerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setScanResultList(List<ScanResult> scanResultList, YmWifiUtils wifiUtils) {
        if (mScanResultList != null) mScanResultList.clear();
        mScanResultList = scanResultList;
        mWifiUtils = wifiUtils;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mScanResultList == null) {
            return 0;
        }
        return mScanResultList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
            convertView = mInflater.inflate(R.layout.wifi_list_item, null);
            holder = new ViewHolder();
            holder.mSecure = (TextView) convertView.findViewById(R.id.secure);
            holder.mSignal = (ImageView) convertView.findViewById(R.id.SignalImage);
            holder.mSsid = (TextView) convertView.findViewById(R.id.ssid);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mScanResultList == null) return convertView;
        ScanResult scanResult = null;
        if (position < mScanResultList.size())
            scanResult = mScanResultList.get(position);


        Log.d(TAG, scanResult.toString());

        if (scanResult != null) {
            Log.d(TAG, TAG + "scanResult:" + scanResult.toString());
            holder.mSsid.setText(scanResult.SSID);

            Log.d(TAG, "wifi : frequency:" + String.valueOf(scanResult.frequency) + "MHZ");

            holder.mSignal.setImageResource(R.drawable.wifi_singal);

            Log.d(TAG, "wifiLevel:" + mWifiUtils.getWifiLevel(scanResult));
            //holder.mSignal.setImageLevel(mWifiUtils.getWifiLevel(scanResult));
            holder.mSignal.getDrawable().setLevel(mWifiUtils.getWifiLevel(scanResult) + 1);

            holder.mSecure.setText(scanResult.capabilities);
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView mSsid;//显示wifi名称。

        private ImageView mSignal;//显示信号图标。

        private TextView mSecure;//显示安全验证的方式。

    }
}
