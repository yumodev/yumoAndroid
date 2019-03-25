package com.yumo.android.test.sys;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import com.yumo.common.io.YmIoUtil;
import com.yumo.common.log.Log;
import com.yumo.common.util.YmDateUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import static android.content.Context.NETWORK_STATS_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by yumodev on 17/9/12.
 * 流量统计
 */

public class TrafficTestView extends YmTestFragment{
    final String LOG_TAG = "TrafficTestView";

    List<ActivityManager.RunningAppProcessInfo> mProcessList = null;

    ActivityManager activityManager = null;

    public TrafficTestView(){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityManager = (ActivityManager) getContext().getSystemService(getContext().ACTIVITY_SERVICE);
    }

    /**
     *
     * android.os.Process.myPid()：获取该进程的ID。
     android.os.Process.myTid()：获取该线程的ID。
     android.os.Process.myUid()：获取该进程的用户ID。
     */
    public void testShowCurrentApp(){
        int uid = android.os.Process.myUid();
        long rx = TrafficStats.getUidRxBytes(uid);
        long tx = TrafficStats.getUidTxBytes(uid);
        Log.i(LOG_TAG, "uid:"+rx+" "+tx);
        Log.i(LOG_TAG, Formatter.formatFileSize(getContext(), rx));
        Log.i(LOG_TAG, Formatter.formatFileSize(getContext(), tx));

        long rxMobile = TrafficStats.getMobileRxBytes();
        long txMobile = TrafficStats.getMobileTxBytes();
        long rxTotal = TrafficStats.getTotalRxBytes();
        long txTotal = TrafficStats.getTotalTxBytes();


        Log.i(LOG_TAG, "uid:"+rxMobile+" "+txMobile+" "+rxTotal+" "+txTotal);
    }

    public void testShowCurrentAppFromFile() {
        int uid = android.os.Process.myUid();
        long rx = getUidRxBytes(uid);
        long tx = getUidTxBytes(uid);
        Log.i(LOG_TAG, "uid:" + rx + " " + tx);
        Log.i(LOG_TAG, Formatter.formatFileSize(getContext(), rx));
        Log.i(LOG_TAG, Formatter.formatFileSize(getContext(), tx));
    }

    public void testShowProcessList(){
        mProcessList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : mProcessList){
            String name = processInfo.processName;
            int pid = processInfo.pid;
            int uid = processInfo.uid;
            Log.i(LOG_TAG, name+" uid:"+uid+" pid:"+pid );
        }
    }

    private long getUidRxBytes(int uid) {
        long rxBytes = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("/proc/uid_stat/" + uid + "/tcp_rcv"));
            String line = in.readLine();
            rxBytes = Long.parseLong(line);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            YmIoUtil.close(in);
        }
        return rxBytes;
    }

   private long getUidTxBytes(int uid) {
        long txBytes = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("/proc/uid_stat/" + uid + "/tcp_snd"));
            String line = in.readLine();
            txBytes = Long.parseLong(line);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
           YmIoUtil.close(in);
        }
        return txBytes;
    }

    public void testNetworkStatsManager(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (!hasPermissionToReadNetworkStats(getContext())){
            return;
        }

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getContext().getSystemService(NETWORK_STATS_SERVICE);
        NetworkStats.Bucket bucket = null;
        // 获取到目前为止设备的Wi-Fi流量统计
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, "", 0, System.currentTimeMillis());
        } catch (RemoteException e) {
            e.printStackTrace();
            showToastMessage("获取流量失败！");
        }

        if (bucket == null){
            return;
        }
        StringBuilder sb = new StringBuilder();
        Log.i("Info", "Total: " + (bucket.getRxBytes() + bucket.getTxBytes()));
        sb.append("total:");
        sb.append(" rx:"+bucket.getRxBytes()+" "+Formatter.formatFileSize(getContext(), bucket.getRxBytes()));
        sb.append(" tx:"+bucket.getTxBytes()+" "+Formatter.formatFileSize(getContext(), bucket.getTxBytes()));
        showToastMessage(sb.toString());
    }

    public void testPrintAllAppTraffic(){
        String result = getAppTraffic(getContext(), false, -1);
        Log.i(LOG_TAG, result);
    }

    public void testPrintCurrntAppTraffic1(){
        String result = getAppTraffic(getContext(), false, -1);
        Log.i(LOG_TAG, result);
    }

    public void testPrintUserTraffic(){
        String result = getUserTraffic(getContext(), ConnectivityManager.TYPE_WIFI);
        Log.i(LOG_TAG, "wifi:"+result);
        result = getUserTraffic(getContext(), ConnectivityManager.TYPE_MOBILE);
        Log.i(LOG_TAG, "mobile:"+result);
        result = getUserTraffic(getContext(), ConnectivityManager.TYPE_VPN);
        Log.i(LOG_TAG, "vpn:"+result);
    }

    public void testPrintCurrentAppTraffic(){
        int uid = android.os.Process.myUid();
        String result = getUserTrafficByUid(getContext(), ConnectivityManager.TYPE_WIFI, uid);
        Log.i(LOG_TAG, "wifi:"+result);
        result = getUserTrafficByUid(getContext(), ConnectivityManager.TYPE_MOBILE, uid);
        Log.i(LOG_TAG, "mobile:"+result);
        result = getUserTrafficByUid(getContext(), ConnectivityManager.TYPE_VPN, uid);
        Log.i(LOG_TAG, "vpn:"+result);
    }

    private String getAppTraffic(Context context, boolean getCurrentApp, int uid){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return "";
        }

        if (!hasPermissionToReadNetworkStats(getContext())){
            return "";
        }

        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String subId = tm.getSubscriberId();

        NetworkStats summaryStats;
        long summaryRx = 0;
        long summaryTx = 0;
        NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getContext().getSystemService(NETWORK_STATS_SERVICE);

        long summaryTotal = 0;

        String result = "";
        try {
            summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_WIFI, subId, YmDateUtil.getTimesMonthMorning(), System.currentTimeMillis());
            StringBuilder sb = new StringBuilder();
            do {
                summaryStats.getNextBucket(summaryBucket);
                int summaryUid = summaryBucket.getUid();
                summaryRx += summaryBucket.getRxBytes();
                summaryTx += summaryBucket.getTxBytes();

                summaryTotal += summaryBucket.getRxBytes() + summaryBucket.getTxBytes();
                if (getCurrentApp){
                    if (uid == summaryBucket.getUid()){
                        sb.append("uid:"+summaryUid);
                        sb.append(" rx:"+summaryBucket.getRxBytes()+" "+Formatter.formatFileSize(context, summaryBucket.getRxBytes()));
                        sb.append(" tx:"+summaryBucket.getTxBytes()+" "+Formatter.formatFileSize(context, summaryBucket.getTxBytes()));
                        sb.append(" total:"+Formatter.formatFileSize(context, summaryTotal));
                        sb.append(" \n");
                    }
                }else{
                    sb.append("uid:"+summaryUid);
                    sb.append(" rx:"+summaryBucket.getRxBytes()+" "+Formatter.formatFileSize(context, summaryBucket.getRxBytes()));
                    sb.append(" tx:"+summaryBucket.getTxBytes()+" "+Formatter.formatFileSize(context, summaryBucket.getTxBytes()));
                    sb.append(" \n");
                }

            } while (summaryStats.hasNextBucket());

            result = sb.toString();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getUserTraffic(Context context, int networkType){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return "";
        }

        if (!hasPermissionToReadNetworkStats(getContext())){
            return "";
        }

        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String subId = tm.getSubscriberId();

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getContext().getSystemService(NETWORK_STATS_SERVICE);

        String result = "";
        try {
            NetworkStats.Bucket bucket = networkStatsManager.querySummaryForUser(networkType, subId, YmDateUtil.getTimesMonthMorning(), System.currentTimeMillis());
            StringBuilder sb = new StringBuilder();
            if (networkType != ConnectivityManager.TYPE_VPN){
                 int summaryUid = bucket.getUid();
                 sb.append("uid:"+summaryUid);
            }

            sb.append(" rx:"+bucket.getRxBytes()+" "+Formatter.formatFileSize(getContext(), bucket.getRxBytes()));
            sb.append(" tx:"+bucket.getTxBytes()+" "+Formatter.formatFileSize(getContext(), bucket.getTxBytes()));
            sb.append(" \n");

            result = sb.toString();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }


    private String getUserTrafficByUid(Context context, int uid, int networkType){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return "";
        }

        if (!hasPermissionToReadNetworkStats(getContext())){
            return "";
        }

        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String subId = tm.getSubscriberId();

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getContext().getSystemService(NETWORK_STATS_SERVICE);

        String result = "";

            NetworkStats.Bucket bucket = new NetworkStats.Bucket();
            NetworkStats networkStats = networkStatsManager.queryDetailsForUid(ConnectivityManager.TYPE_WIFI, "", YmDateUtil.getTimesMonthMorning(), System.currentTimeMillis(), uid);
            StringBuilder sb = new StringBuilder();
            while (networkStats.hasNextBucket()){
                networkStats.getNextBucket(bucket);
                if (networkType != ConnectivityManager.TYPE_VPN){
                    int summaryUid = bucket.getUid();
                    sb.append("uid:"+summaryUid);
                }

                sb.append(" rx:"+bucket.getRxBytes()+" "+Formatter.formatFileSize(getContext(), bucket.getRxBytes()));
                sb.append(" tx:"+bucket.getTxBytes()+" "+Formatter.formatFileSize(getContext(), bucket.getTxBytes()));
                sb.append(" \n");
            }


            result = sb.toString();

        return result;
    }

    public  boolean hasPermissionToReadNetworkStats(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }

        requestReadNetworkStats(context);
        return false;
    }

    // 打开“有权查看使用情况的应用”页面
    private void requestReadNetworkStats(Context context) {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        context.startActivity(intent);
    }

}
