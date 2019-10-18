package com.yumo.android.test.sys;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import com.yumo.android.common.YumoConfig;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.anno.YmMethodTest;
import com.yumo.demo.view.YmTestFragment;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ShuangkaiTestView extends YmTestFragment {

  @YmMethodTest(name = "LBE平行空间双开检测")
  public void isLbeShuangkai() {
    checkBySystem();
  }

  /*
   LBE：com.lbe.doubleagent.client.hook.BinderProxy
   超级分身助手:$Proxy7
   分身大师：msdocker.af$a
   多开分身：com.bly.chaos.plugin.hook.base.c
   */
  private boolean checkBySystem(){
    try {
      Class serviceManager = null;
      serviceManager = Class.forName("android.os.ServiceManager");
      Method method = serviceManager.getMethod("getService", String.class);
      Object phone =  method.invoke(serviceManager.newInstance(), "phone");
      if ( phone != null && phone.getClass().getName() != "android.os.BinderProxy") {
        showToastMessage(phone.getClass().getName()+"    hook");
      }else{
        showToastMessage(phone.getClass().getName());
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      showToastMessage(e.getMessage());
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      showToastMessage(e.getMessage());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      showToastMessage(e.getMessage());
    } catch (java.lang.InstantiationException e) {
      e.printStackTrace();
      showToastMessage(e.getMessage());
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      showToastMessage(e.getMessage());
    }
    return true;
  }

  /**
   * 获取IEMI， 只能支持拥有通话功能的设备，返厂不支持。 需要android.permission.READ_PHONE_STATE， 部分rom有可能有bug。
   * 0000000000000
   */
  @YmMethodTest(name = "IMEI-第一个")
  public void testImei() {
    TelephonyManager manager =
        (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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

  @YmMethodTest(name = "获取AndroidID")
  public void testGetAndroidId() {
    final String androidId = Settings.Secure.getString(
        getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    showToastMessage(androidId);
  }

  /**
   * 2.3以后可用，获取序列号
   */
  @YmMethodTest(name = "获取序列号")
  public void testSerialNumber() {
    showToastMessage(Build.SERIAL);
  }

  @YmMethodTest(name = "生成一个sd卡文件")
  public void testSaveFile() {
    String filePath = YumoConfig.getFileDirectory() + File.separator + "shuangkai.txt";
    YmFileUtil.saveFile(filePath, "测试双开："+(new Random()).nextInt(100));
    showToastMessage("保存成功");
  }

  @YmMethodTest(name = "测试双开Sd文件是否存在")
  public void tesSkFileExist() {
    String filePath = YumoConfig.getFileDirectory() + File.separator + "shuangkai.txt";
    showToastMessage(filePath + " 是否存在：" + YmFileUtil.isExistFile(filePath));
  }

  @YmMethodTest(name = "测试读取双开Sd卡文件")
  public void testReadFile() {
    String filePath = YumoConfig.getFileDirectory() + File.separator + "shuangkai.txt";
    if (YmFileUtil.isExistFile(filePath)) {
      showToastMessage(YmFileUtil.readToString(filePath));
    } else {
      showToastMessage(filePath + " 是否存在：" + YmFileUtil.isExistFile(filePath));
    }
  }

  public void testShowSdDir(){
    showToastMessage(YumoConfig.getFileDirectory());
  }


  public void testCheckByPath() {
    String path = getContext().getFilesDir().getAbsolutePath();
    if (path != null && path.length() > 0) {
      if (path.startsWith("/data/user/0/") || path.startsWith("/data/data/")){
        showToastMessage("本尊");
      }else{
        showToastMessage("运行在分身："+path);
      }
    }
  }

  public void testCheckByPackageName(){
    boolean result = checkByPackageName(getContext());
    if (result){
      showToastMessage("运行在分身");
    }else{
      showToastMessage("本尊");
    }
  }

  /**
   * 分身APP的中会有获取App列表，会有两个自己
   * @param context
   * @return
   */
  private boolean checkByPackageName(Context context) {
    try {
      if (context == null) {
        return false;
      }
      int count = 0;
      String packageName = context.getPackageName();
      PackageManager pm = context.getPackageManager();
      List<PackageInfo> pkgs = pm.getInstalledPackages(0);
      for (PackageInfo info : pkgs) {
        if (packageName.equals(info.packageName)) {
          count++;
        }
      }
      return count > 1;
    } catch (Exception ignore) {}
    return false;
  }
  public void testCheckoutByPs(){
    boolean result = isRunInVirtual();
    if (result){
      showToastMessage("运行在分身");
    }else{
      showToastMessage("本尊");
    }
  }

  public static boolean isRunInVirtual() {
    String filter = getUidStrFormat();

    if (TextUtils.isEmpty(filter)) {
      return false;
    }
    String result = exec("ps");
    if (result == null || result.isEmpty()) {
      return false;
    }

    String[] lines = result.split("\n");
    if (lines == null || lines.length <= 0) {
      return false;
    }

    int exitDirCount = 0;
    try {
      for (int i = 0; i < lines.length; i++) {
        if (lines[i].contains(filter)) {
          int pkgStartIndex = lines[i].lastIndexOf(" ");
          String processName = lines[i].substring(pkgStartIndex <= 0
              ? 0 : pkgStartIndex + 1, lines[i].length());
          File dataFile = new File(String.format("/data/data/%s",
              processName, Locale.CHINA));
          if (dataFile.exists()) {
            exitDirCount++;
          }
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return exitDirCount > 1;
  }


  private static String exec(String command) {
    BufferedOutputStream bufferedOutputStream = null;
    BufferedInputStream bufferedInputStream = null;
    Process process = null;
    try {
      process = Runtime.getRuntime().exec("sh");
      bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());

      bufferedInputStream = new BufferedInputStream(process.getInputStream());
      bufferedOutputStream.write(command.getBytes());
      bufferedOutputStream.write('\n');
      bufferedOutputStream.flush();
      bufferedOutputStream.close();

      process.waitFor();

      String outputStr = getStrFromBufferInputSteam(bufferedInputStream);
      return outputStr;
    } catch (Exception e) {
      return null;
    } finally {
      if (bufferedOutputStream != null) {
        try {
          bufferedOutputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (bufferedInputStream != null) {
        try {
          bufferedInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (process != null) {
        process.destroy();
      }
    }
  }

  private static String getStrFromBufferInputSteam(BufferedInputStream bufferedInputStream) {
    if (null == bufferedInputStream) {
      return "";
    }
    int BUFFER_SIZE = 512;
    byte[] buffer = new byte[BUFFER_SIZE];
    StringBuilder result = new StringBuilder();
    try {
      while (true) {
        int read = bufferedInputStream.read(buffer);
        if (read > 0) {
          result.append(new String(buffer, 0, read));
        }
        if (read < BUFFER_SIZE) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result.toString();
  }

  public static String getUidStrFormat() {
    String filter = exec("cat /proc/self/cgroup");
    if (filter == null || filter.length() == 0) {
      return null;
    }

    int uidStartIndex = filter.lastIndexOf("uid");
    int uidEndIndex = filter.lastIndexOf("/pid");
    if (uidStartIndex < 0) {
      return null;
    }
    if (uidEndIndex <= 0) {
      uidEndIndex = filter.length();
    }

    filter = filter.substring(uidStartIndex + 4, uidEndIndex);
    try {
      String strUid = filter.replaceAll("\n", "");
      if (isNumber(strUid)) {
        int uid = Integer.valueOf(strUid);
        filter = String.format("u0_a%d", uid - 10000);
        return filter;
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean isNumber(String str) {
    if (str == null || str.length() == 0) {
      return false;
    }
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
