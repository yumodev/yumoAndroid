package com.yumo.android.test.sys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.format.Formatter;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.android.YmContext;
import com.yumo.common.android.YmDeviceUtil;
import com.yumo.common.define.YmDefine;
import com.yumo.common.log.Log;
import com.yumo.demo.anno.YmMethodTest;
import com.yumo.demo.view.YmTestFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AppInfoTestView extends YmTestFragment {


    @SuppressLint("NewApi")
    @YmMethodTest(name = "打印APP目录")
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
        Log.i(YmDefine.LOG_TAG_LIB, sb.toString());
    }

    /**
     * 获取签名信息
     */
    @YmMethodTest(name = "手机签名")
    public void testSignatures() {
        showToastMessage(getSignatures(getContext()));
    }

    /**
     * 打印所有的签名信息
     */
    @YmMethodTest(name = "打印所有App的签名信息")
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
            Log.i(YmDefine.LOG_TAG_LIB, sb.toString());
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
    @YmMethodTest(name = "打印所有App的SHA1")
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
            Log.i(YmDefine.LOG_TAG_LIB, sb.toString());
        }

    }


    /**
     * 显示当前的SHA1
     */
    @YmMethodTest(name = "显示当前App的Sha1")
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
            showToastMessage(result);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断当前是否位系统签名
     */
    @YmMethodTest(name = "当前的App的签名是否为系统签名")
    public void testIsSystemSha1() {
        String sha1 = YmAppUtil.getAppSha1(getContext());
        if ("27:19:6E:38:6B:87:5E:76:AD:F7:00:E7:EA:84:E4:C6:EE:E3:3D:FA".equals(sha1)) {
            showToastMessage("是系统签名");
        } else {
            showToastMessage("不是系统签名");
        }
    }
}
