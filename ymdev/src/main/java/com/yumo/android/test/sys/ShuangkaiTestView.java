package com.yumo.android.test.sys;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import androidx.core.app.ActivityCompat;
import com.yumo.android.common.YumoConfig;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.anno.YmMethodTest;
import com.yumo.demo.view.YmTestFragment;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
      if (phone.getClass().getName() != "android.os.BinderProxy") {
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
}
