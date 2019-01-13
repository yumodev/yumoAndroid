/**
 * SystemInfoTestView.java
 * yumodev
 * 2015-7-30
 */
package com.yumodev.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Formatter;

import com.yumo.common.android.YmContext;
import com.yumo.common.android.YmDeviceUtil;
import com.yumo.common.log.Log;
import com.yumo.common.util.YmDateUtil;
import com.yumo.demo.view.YmTestFragment;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 打印手机信息
 * [如何获取Android唯一标识（唯一序列号）](https://www.cnblogs.com/fnlingnzb-learner/p/7580691.html)
 */
public class SystemInfoTestView extends YmTestFragment {
    private final String LOG_TAG = "YmDev";

    public void testShowAppInfo() {
        StringBuilder sb = new StringBuilder();
        //获取程序最大的可分配的内存
        String appMaxMemory = Formatter.formatFileSize(YmContext.getInstance().getAppContext(), YmDeviceUtil.getAppMaxMemory());

        sb.append("\n应用程序可分配的最大内存：" + appMaxMemory + " ");
        showToastMessage(sb.toString());
    }

    @SuppressLint("NewApi")
    public void testPrintSystemDir() {
        StringBuilder sb = new StringBuilder();
        //data/data/<package>/cache
        sb.append("\ngetCacheDir():" + getContext().getCacheDir());
        //data/data/<package>/files
        sb.append("\ngetFilesDir():" + getContext().getFilesDir());
        //data/data/<package>
        sb.append("\ngetDataDir():" + getContext().getDataDir());
        //data/data/<package>/app_
        sb.append("\ngetDir():" + getContext().getDir("", 0));
        //data/data/<package>/code_cache
        sb.append("\ngetCodeCacheDir():" + getContext().getCodeCacheDir());
        ///data/app/com.yumo.android-1/base.apk
        sb.append("\ngetPackageCodePath():" + getContext().getPackageCodePath());


        ///storage/emulated/0/Android/data/com.yumo.android/cache
        sb.append("\ngetExternalCacheDir():" + getContext().getExternalCacheDir().getAbsolutePath());
        ///storage/emulated/0/Android/data/com.yumo.android/files
        sb.append("\ngetExternalFilesDir():" + getContext().getExternalFilesDir(null));

        sb.append("\ngetObbDir():" + getContext().getObbDir());
        showToastMessage(sb.toString());
        Log.i(LOG_TAG, sb.toString());
    }

    /**
     * 获取开机时间
     */
    public void testRootTime() {
        long time = SystemClock.elapsedRealtime();
        String str = YmDateUtil.formatDuration(time);
        //String str = DateUtils.formatElapsedTime(time / 1000);
        //String str = DateUtils.formatDateRange(getContext(), 0, time, DateUtils.FORMAT_24HOUR);

        showToastMessage(time + " " + str);
    }

    /**
     * 当前线程运行时间
     */
    public void testCurrentThreadTime() {
        long time = SystemClock.currentThreadTimeMillis();
        //String str = YmDateUtil.formatDuration(time);
        String str = DateUtils.formatElapsedTime(time / 1000);
        showToastMessage(time + " " + str);

    }

    /**
     * 获取签名信息
     */
    public void testSignatures() {
        showToastMessage(getSignatures(getContext()));
    }

    /**
     * 打印所有的签名信息
     */
    public void testPrintAllSingatures() {
        PackageManager pm = getContext().getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            StringBuilder sb = new StringBuilder();
            sb.append("Name:" + packageinfo.applicationInfo.loadLabel(pm).toString());
            sb.append(" " + packageinfo.packageName);
            sb.append(" " + packageinfo.signatures[0].toCharsString());
            sb.append("\n");
            Log.i(LOG_TAG, sb.toString());
        }
    }

    /**
     * 获取当前签名
     * @param context
     * @return
     */
    private String getSignatures(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            String packageName = packageinfo.packageName;
            if (packageName.equals(context.getPackageName())) {
                return packageinfo.signatures[0].toCharsString();
            }
        }
        return null;
    }

    /**
     * 打印所有的SHA1
     */
    public void testPrintSHA1() {
        PackageManager pm = getContext().getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            StringBuilder sb = new StringBuilder();
            sb.append("Name:" + packageinfo.applicationInfo.loadLabel(pm).toString());
            sb.append(" " + packageinfo.packageName);
            //sb.append(" "+packageinfo.signatures[0].toCharsString());

            byte[] cert = packageinfo.signatures[0].toByteArray();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }

            String result = hexString.toString();
            result = result.substring(0, result.length() - 1);
            sb.append(" " + result);

            sb.append("\n");
            Log.i(LOG_TAG, sb.toString());
        }

    }


    /**
     * 显示当前的SHA1
     */
    public void testShowSha1() {
        try {
            Context context = getActivity().getApplicationContext();
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }

            String result = hexString.toString();
            result = result.substring(0, result.length() - 1);
            android.util.Log.i(LOG_TAG, result);
            showToastMessage(result);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取IEMI， 只能支持拥有通话功能的设备，返厂不支持。 需要android.permission.READ_PHONE_STATE， 部分rom有可能有bug。
     * 0000000000000
     */
    public void testImei() {
        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            showToastMessage("未获取权限");
            return;
        }
        String imei = manager.getDeviceId();
        showToastMessage(imei);
    }

    /**
     * 获取MAC地址， 如果WIFI没有打开，也不会返回，不建议使用
     */
    public void testGetMac() {
        String mac = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            mac = wifiInfo.getMacAddress();
        } else {
            NetworkInterface networkInterface = null;
            try {
                networkInterface = NetworkInterface.getByName("wlan0");
                byte[] macBytes = networkInterface.getHardwareAddress();
                mac = new String(macBytes);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        showToastMessage(mac);
    }

    public void testGetBluetoothAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMac = bluetoothAdapter.getAddress();
        String bluetoothName = bluetoothAdapter.getName();
        showToastMessage(bluetoothName + " mac:" + bluetoothMac);
    }


    /**
     * 设备首次启动时，系统会随机生成一个64位数字。
     * 设备恢复出厂设置后会充值。
     * 获取Androidid, 2.2手机有bug，返回固定数字：9774d56d682e549c
     * 返厂或者Root后会发生改变
     */
    public void testGetAndroidId() {
        final String androidId = Settings.Secure.getString(
                getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        showToastMessage(androidId);
    }

    /**
     * 2.3以后可用，获取序列号
     */
    public void testSerialNumber() {
        showToastMessage(Build.SERIAL);
    }

    /**
     * 2.3以后可用，获取序列号
     * 获取失败
     */
    public void testSerialNumber1() {
        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            showToastMessage("未获取权限");
            return;
        }
        String serialNumber = manager.getSimSerialNumber();
        showToastMessage(serialNumber);
    }

    /**
     * 和Build.SERIAL 获取的不一致
     */
    public void testSerialNumber2() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
            System.out.println(serial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showToastMessage(serial);
    }

    /**
     * 2.3以后可用，获取序列号
     * 获取失败
     */
    public void testSerialNumber3() {
        String serialNumber = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            serialNumber = Build.getSerial();
        }
        showToastMessage(serialNumber);
    }



    public void testUnicodeId(){
        showToastMessage(new UUID(Build.SERIAL.hashCode(), Build.SERIAL.hashCode()).toString());
    }

    /**
     * 获取手机号
     */
    public void testGetPhoneNum(){
        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            showToastMessage("未获取权限");
            return;
        }
        String phone = manager.getLine1Number();

        if (TextUtils.isEmpty(phone) || phone.length() < 11){
            showToastMessage(phone);
            return;
        }

        phone = phone.substring(phone.length()-11);
        if (!phone.matches( "^\\d{11}$")){
            showToastMessage("非法："+phone);
        }

        showToastMessage(phone);
    }


    /**
     * 检测和打开和关闭飞行模式
     */
    public void testAirplaneMode(){
        ContentResolver cr = getActivity().getContentResolver();
        if(Settings.System.getString(cr,Settings.System.AIRPLANE_MODE_ON).equals("0")){
            showToastMessage("已关闭飞行模式");
            //获取当前飞行模式状态,返回的是String值0,或1.0为关闭飞行,1为开启飞行
            //如果关闭飞行,则打开飞行
            Settings.System.putString(cr,Settings.System.AIRPLANE_MODE_ON, "1");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            getActivity().sendBroadcast(intent);

        }else{
            showToastMessage("已打开飞行模式");
            //否则关闭飞行
            Settings.System.putString(cr,Settings.System.AIRPLANE_MODE_ON, "0");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            getActivity().sendBroadcast(intent);
        }

    }
}
