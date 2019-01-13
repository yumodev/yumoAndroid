package com.yumodev.process.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.util.Arrays;
import java.util.HashMap;

public class TestBluetooth extends YmTestFragment {
    private final String TAG = "ymbluttooth";

    private BluetoothAdapter.LeScanCallback mCallback = null;
    private HashMap<String, BluetoothDevice> mDeviceMap = new HashMap<>();
    public void testHasBluetooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            showToastMessage("不支持蓝牙！");
        }else{
            showToastMessage("支持蓝牙");
        }
    }

    public void testIsEnable(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void testLeScan(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                String msg = "rssi:"+rssi+" "+formatString(device);
                showToastMessageOnUiThread(msg);
                mDeviceMap.put(device.getAddress(), device);
                Log.i(TAG, msg);
            }
        };
        bluetoothAdapter.startLeScan(mCallback);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void testStopLeScan(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.stopLeScan(mCallback);
        mDeviceMap.clear();;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private String formatString(BluetoothDevice device){
        StringBuilder sb = new StringBuilder();
        sb.append("name:").append(device.getName()).append(" ");
        sb.append("address:").append(device.getAddress()).append(" ");
        sb.append("type:").append(device.getType()+"").append(" ");
        sb.append("state:").append(device.getBondState()+"").append(" ");
        sb.append("uuids:").append(Arrays.toString(device.getUuids())).append(" ");
        sb.append("\n");
        return sb.toString();
    }
}
