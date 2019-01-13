package com.yumo.common.android;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.yumo.common.define.YmDefine;
import com.yumo.common.log.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

/**
 * Created by yumodev on 17/2/24.
 * App相关的工具类
 */

public class YmAppUtil {

    /**
     * 获取APP的版本号
     * yumodev
     * @param context
     * @return int
     * 2015-1-18
     */
    public static int getAppVersion(Context context) {
        if (context == null) {
            return 0;
        }

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取App的名字
     * @param context
     * @return
     */
    public static String getAppLabel(Context context) {
        if (context == null) {
            return null;
        }

        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    /**
     * 获取一个APP的Uid
     * @param context
     * @param packageName
     * @return 返回-1标识获取失败
     */
    public static int getUid(Context context, String packageName) {
        int uid = -1;
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            uid = applicationInfo.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return uid;
    }


    /**
     * 实现文本复制
     * @param context
     * @param content
     */
    public static void copy(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);
    }

    /**
     * 粘贴
     * @param context
     * @return
     */
    public static String paste(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        String content = cmb.getText().toString().trim();
        return content;
    }

    /**
     * 分享
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static boolean share(Context context, String title, String content) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, content);
        send.putExtra(Intent.EXTRA_SUBJECT, title);
        try {
            context.startActivity(Intent.createChooser(send, content));
        } catch (android.content.ActivityNotFoundException ex) {
            return false;
        }
        return true;
    }


    /**
     * 是否是否正在运行。
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        if (context == null) {
            return false;
        }

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ;
        List<ActivityManager.RunningServiceInfo> lists = am.getRunningServices(1000);
        for (ActivityManager.RunningServiceInfo info : lists) {
            if (info.service.getClassName().equals(className)
                    && info.service.getPackageName().equals(context.getPackageName())) {
                isRunning = true;
                break;
            }
        }

        return isRunning;
    }

    /**
     * 判断当前是否飞行模式
     * @param context
     * @return
     */
    public static boolean isAirplane(Context context) {
        ContentResolver crs = context.getContentResolver();
        String state = Settings.System.getString(crs, Settings.System.AIRPLANE_MODE_ON);
        if (state.equals("1")) {
            return true;
        }
        return false;
    }


    //下载到本地后执行安装
    public static boolean installAPK(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取签名的SHA1
     * @param context
     * @return
     */
    public static String getAppSha1(Context context) {
        String result = "";
        try {
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

            result = hexString.toString();
            result = result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            result = e.getMessage();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }


    /**
     * 执行adb命令
     * @param adb
     * @return
     */
    public static String execShell(String adb) {
        BufferedReader reader = null;
        String content = "";
        try {
            Process process = Runtime.getRuntime().exec(adb);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            content = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    public static String execRootShell(String cmd) {
        Process p;
        DataOutputStream os = null;
        InputStream is = null;
        //SystemProperties.set("persist.sys.root_access", "2");
        try {
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            is = p.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            StringBuffer result = new StringBuffer();
            String line;
            while ((line = bf.readLine()) != null) {
                result.append(line);
            }
            os.flush();
            int ret = p.waitFor();
            if (ret == 0) {
                Log.d(YmDefine.LOG_TAG_LIB, "execRootShell success");
                return result.toString();
            } else {
                Log.d(YmDefine.LOG_TAG_LIB, "execRootShell failed");
                return "0";
            }

        } catch (IOException e) {
            Log.d(YmDefine.LOG_TAG_LIB, "execRootShell " + e.getMessage());
        } catch (InterruptedException e) {
            Log.d(YmDefine.LOG_TAG_LIB, "execRootShell " + e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "0";
    }


    public static void hookWebView() {
        int sdkInt = Build.VERSION.SDK_INT;
        try {
            Class<?> factoryClass = Class.forName("android.webkit.WebViewFactory");
            Field field = factoryClass.getDeclaredField("sProviderInstance");
            field.setAccessible(true);
            Object sProviderInstance = field.get(null);
            if (sProviderInstance != null) {
                Log.d(YmDefine.LOG_TAG_LIB, "sProviderInstance isn't null");
                return;
            }
            Method getProviderClassMethod;
            if (sdkInt > 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
            } else if (sdkInt == 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
            } else {
                Log.d(YmDefine.LOG_TAG_LIB, "Don't need to Hook WebView");
                return;
            }
            getProviderClassMethod.setAccessible(true);
            Class<?> providerClass = (Class<?>) getProviderClassMethod.invoke(factoryClass);
            Class<?> delegateClass = Class.forName("android.webkit.WebViewDelegate");
            Constructor<?> providerConstructor = providerClass.getConstructor(delegateClass);
            if (providerConstructor != null) {
                providerConstructor.setAccessible(true);
                Constructor<?> declaredConstructor = delegateClass.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                sProviderInstance = providerConstructor.newInstance(declaredConstructor.newInstance());
                Log.d(YmDefine.LOG_TAG_LIB, "sProviderInstance:{}" + sProviderInstance);
                field.set("sProviderInstance", sProviderInstance);
            }
            Log.d(YmDefine.LOG_TAG_LIB, "Hook done!");
        } catch (Throwable e) {
            Log.e(YmDefine.LOG_TAG_LIB, e.getMessage());
        }
    }

    /**
     * 获取用户id
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String tmDeviceID = tm.getDeviceId();

            if (TextUtils.isEmpty(tmDeviceID) || tmDeviceID.length() > 32){
                return "";
            }
            return tmDeviceID;
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
