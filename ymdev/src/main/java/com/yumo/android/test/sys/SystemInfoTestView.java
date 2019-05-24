/**
 * SystemInfoTestView.java
 * yumodev
 * 2015-7-30
 */
package com.yumo.android.test.sys;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.android.YmContext;
import com.yumo.common.android.YmDeviceUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

/**
 * 打印手机信息
 * [android.os.Build 常用常量](https://blog.csdn.net/dzkdxyx/article/details/78879521)
 */
public class SystemInfoTestView extends YmTestFragment {
    private final String LOG_TAG = "YmDev";

    public void testShowAppInfo() {
        StringBuilder sb = new StringBuilder();
        //获取程序最大的可分配的内存
        String appMaxMemory = Formatter.formatFileSize(YmContext.getInstance().getAppContext(), YmDeviceUtil.getAppMaxMemory());

        sb.append("\n应用程序最大内存：" + Formatter.formatFileSize(getActivity(), Runtime.getRuntime().maxMemory()) + " ");
        sb.append("\n应用程序总共内存：" + Formatter.formatFileSize(getActivity(), Runtime.getRuntime().totalMemory()) + " ");
        sb.append("\n应用程序空闲存：" + Formatter.formatFileSize(getActivity(), Runtime.getRuntime().freeMemory()) + " ");
        sb.append("\nAPP内存："+ String.format("%02f", getRam(getActivity().getApplicationContext(), true)));

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

    public void testShowSystemInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(android.os.Build.BOARD+"\n");//获取设备基板名称
        sb.append(android.os.Build.BOOTLOADER+"\n");//获取设备引导程序版本号
        sb.append(android.os.Build.BRAND+"\n");//获取设备品牌
        sb.append(android.os.Build.CPU_ABI+"\n");//获取设备指令集名称（CPU的类型）
        sb.append(android.os.Build.CPU_ABI2+"\n");//获取第二个指令集名称
        sb.append(android.os.Build.DEVICE+"\n");//获取设备驱动名称
        sb.append(android.os.Build.DISPLAY+"\n");//获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
        sb.append(android.os.Build.FINGERPRINT+"\n");//设备的唯一标识。由设备的多个信息拼接合成。
        sb.append(android.os.Build.HARDWARE+"\n");//设备硬件名称,一般和基板名称一样（BOARD）
        sb.append(android.os.Build.ID+"\n");//设备版本号。
        sb.append(android.os.Build.MODEL+"\n");//获取手机的型号 设备名称。
        sb.append(android.os.Build.MANUFACTURER+"\n");//获取设备制造商
        sb.append(android.os.Build.PRODUCT+"\n");//整个产品的名称
        sb.append(android.os.Build.TAGS+"\n");//设备标签。如release-keys 或测试的 test-keys
        sb.append(android.os.Build.TYPE+"\n");//设备版本类型 主要为 "user" 或 "eng".
        sb.append(android.os.Build.USER+"\n");//设备用户名 基本上都为android -build
        sb.append(android.os.Build.VERSION.RELEASE+"\n");//获取系统版本字符串。如4.1.2 或2.2 或2.3等
        sb.append(android.os.Build.VERSION.CODENAME+"\n");//设备当前的系统开发代号，一般使用REL代替
        sb.append(android.os.Build.VERSION.INCREMENTAL+"\n");//系统源代码控制值，一个数字或者git hash值
        sb.append(android.os.Build.VERSION.SDK_INT+"\n");//系统的API级别 数字表示
        sb.append(android.os.Build.SERIAL+"\n");

        Log.i(LOG_TAG, sb.toString());
        showToastMessage(sb.toString());
    }

    public void testBuildModel(){
        showToastMessage(Build.MODEL);
    }

    public void testBuildBrand(){
        showToastMessage(Build.BRAND);
    }

    public void testBuildProduct(){
        showToastMessage(Build.PRODUCT);
    }

    public void testBuildManufacturer(){
        showToastMessage(Build.MANUFACTURER);
    }


    /**
     * 获取开机时间
     */
    Long mLastelapsedRealtime;
    Long mLastTime;
    public void testRootTime() {
        long time = SystemClock.elapsedRealtime();
        //String str = YmDateUtil.formatDuration(time);
        String str = DateUtils.formatElapsedTime(time / 1000);
        //String str = DateUtils.formatDateRange(getContext(), 0, time, DateUtils.FORMAT_24HOUR);
        mLastTime = System.currentTimeMillis();
        mLastelapsedRealtime = SystemClock.elapsedRealtime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long time = SystemClock.elapsedRealtime();
                    //String str = YmDateUtil.formatDuration(time);
                    String str = DateUtils.formatElapsedTime(time / 1000);
                    long differ = mLastTime+ SystemClock.elapsedRealtime() - mLastelapsedRealtime - System.currentTimeMillis();

                    Log.i("yumodev", time+" "+str+" "+differ);

                }

            }
        }).start();
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
     *
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
     * 判断当前是否位系统签名
     */
    public void testIsSystemSha1() {
        String sha1 = YmAppUtil.getAppSha1(getContext());
        if ("27:19:6E:38:6B:87:5E:76:AD:F7:00:E7:EA:84:E4:C6:EE:E3:3D:FA".equals(sha1)) {
            showToastMessage("是系统签名");
        } else {
            showToastMessage("不是系统签名");
        }
    }


    /**
     * 获取MAC地址， 如果WIFI没有打开，也不会返回，不建议使用
     */
    public void testGetMac() {
        String mac = "";
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        mac = wifiInfo.getMacAddress();

        showToastMessage(mac);
    }

    /**
     * 获取MAC地址， 如果WIFI没有打开，也不会返回，不建议使用
     */
    public void testGetMac2() {
        String mac = "";
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("wlan0");
            byte[] macBytes = networkInterface.getHardwareAddress();
            mac = new String(macBytes);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        showToastMessage(mac);
    }


    /**
     * 必须打开过WIFI一次后才能获取mac
     *
     * 手机上有提示，不可取。
     */
    @Deprecated
    public void testGetMac1() {
        String macAddress = null;
        WifiManager wifiManager =
                (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());

        if (!wifiManager.isWifiEnabled()) {
            //必须先打开，才能获取到MAC地址
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(false);
        }
        if (null != info) {
            macAddress = info.getMacAddress();
        }
        showToastMessage(macAddress);
    }


    public void  testgetLocalMacAddress() {
        String mac;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mac = getMachineHardwareAddress();
        } else {
            WifiManager wifi = (WifiManager) getActivity().getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            mac = info.getMacAddress().replace(":", "");
        }
        showToastMessage(mac);
    }

    public void testIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        //return inetAddress.getHostAddress();
                        showToastMessage(inetAddress.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void testGetIpAddress(){
        showToastMessage(SystemInfoTestView.getIpAddress(getContext()));
    }

    public static String getIpAddress(Context context){
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // 3/4g网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //  wifi网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
                return ipAddress;
            }  else if (info.getType() == ConnectivityManager.TYPE_ETHERNET){
                // 有限网络
                return getLocalIp();
            }
        }
        return null;
    }

    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    // 获取有限网IP
    private static String getLocalIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return "0.0.0.0";
    }

    /**
     * 获取设备的mac地址和IP地址（android6.0以上专用）
     * @return
     */
    public static String getMachineHardwareAddress(){
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if(hardWareAddress == null) continue;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        if(iF != null && iF.getName().equals("wlan0")){
            hardWareAddress = hardWareAddress.replace(":","");
        }
        return hardWareAddress ;
    }

    /***
     * byte转为String
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes){
        if (bytes == null || bytes.length == 0) {
            return null ;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * 获取蓝牙地址。
     * 不需要动态权限
     */
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
     *
     * 不需要动态权限
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

    public void testSericalNumber3() {
        String serial = "unknown";
        try {
            Class clazz = Class.forName("android.os.Build");
            Class paraTypes = Class.forName("java.lang.String");
            Method method = clazz.getDeclaredMethod("getString", paraTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            serial = (String) method.invoke(new Build(), "ro.serialno");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        showToastMessage(serial);


    }

    public Object invokeDeclaredMethod(Object obj, String methodName, Object[] params) {
        if (obj == null || TextUtils.isEmpty(methodName)) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        try {
            Class<?>[] paramTypes = null;
            if (params != null) {
                paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; ++i) {
                    paramTypes[i] = params[i].getClass();
                }
            }
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (NoSuchMethodException e) {
            Log.i("reflect", "method " + methodName + " not found in " + obj.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void testSerialNumber5() {
        String serial = "unknown";
        try {
            serial = String.valueOf(invokeDeclaredMethod(new Build(), "getString", new Object[]{"gsm.serial"}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        showToastMessage(serial);
    }

    public void testSerialNumber6() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "gsm.serial");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(serial)) {
            showToastMessage("获取为空");
            return;
        }

        String[] list = serial.split(" ");

        showToastMessage(list[0]);
    }

    /**
     * 2.3以后可用，获取序列号
     * 获取失败
     */
    public void testSerialNumber4() {
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


    public void testUnicodeId() {
        showToastMessage(new UUID(Build.SERIAL.hashCode(), Build.SERIAL.hashCode()).toString());
    }

    /**
     * 获取手机号
     */
    public void testGetPhoneNum() {
        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            showToastMessage("未获取权限");
            return;
        }
        String phone = manager.getLine1Number();

        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            showToastMessage(phone);
            return;
        }

        phone = phone.substring(phone.length() - 11);
        if (!phone.matches("^\\d{11}$")) {
            showToastMessage("非法：" + phone);
        }

        showToastMessage(phone);
    }


    /**
     * 检测和打开和关闭飞行模式
     */
    public void testAirplaneMode() {
        ContentResolver cr = getActivity().getContentResolver();
        if (Settings.System.getString(cr, Settings.System.AIRPLANE_MODE_ON).equals("0")) {
            showToastMessage("已关闭飞行模式");
            //获取当前飞行模式状态,返回的是String值0,或1.0为关闭飞行,1为开启飞行
            //如果关闭飞行,则打开飞行
            Settings.System.putString(cr, Settings.System.AIRPLANE_MODE_ON, "1");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            getActivity().sendBroadcast(intent);

        } else {
            showToastMessage("已打开飞行模式");
            //否则关闭飞行
            Settings.System.putString(cr, Settings.System.AIRPLANE_MODE_ON, "0");
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            getActivity().sendBroadcast(intent);
        }

    }


    public void testSetTimeZone() {
        try {
            String timeZone = TimeZone.getDefault().getDisplayName();
            final Calendar now = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone(timeZone);
            now.setTimeZone(tz);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testSetSysDate() {
        try {
            SystemClock.setCurrentTimeMillis(System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取总的RAM内存.单位为KB，如果格式后单位为GB
     * void
     * 2015-7-1
     */
    @SuppressLint("NewApi")
    public float getRam(Context context, boolean format) {
        if (context == null){
            return 0f;
        }

        float totalRam = 0f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityManager.MemoryInfo ramInfo = new ActivityManager.MemoryInfo();


            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) {
                return 0f;
            }

            activityManager.getMemoryInfo(ramInfo);
            totalRam = ramInfo.totalMem / 1024;
            if (format) {
                totalRam = totalRam / (1024 * 1024);
            }
        } else {
            String file_path = "/proc/meminfo";
            String ram_info;
            String[] arrayOfRam;

            try {
                FileReader fr = new FileReader(file_path);
                BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
                ram_info = localBufferedReader.readLine();
                arrayOfRam = ram_info.split("\\s+");

                totalRam = Float.valueOf(arrayOfRam[1]).intValue();
                localBufferedReader.close();
                if (format) {
                    totalRam = totalRam / (1024 * 1024);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return totalRam;
    }


    public void testIsScreenOn(){
        PowerManager powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        boolean screen = powerManager.isScreenOn();
        showToastMessage("屏幕状态："+screen);
    }




}
