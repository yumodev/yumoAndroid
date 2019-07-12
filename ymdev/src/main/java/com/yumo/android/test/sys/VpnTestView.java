package com.yumo.android.test.sys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.VpnService;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.yumo.android.common.YumoConfig;
import com.yumo.common.io.ConvertObjectToString;
import com.yumo.common.log.Log;
import com.yumo.common.util.YmStringUtil;
import com.yumo.demo.anno.YmMethodTest;
import com.yumo.demo.view.YmTestFragment;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VpnTestView extends YmTestFragment {

  @YmMethodTest(name = "测试VPN连接是否存在")
  public void testVpnExit(){
    showToastMessage("是否已连接："+isVPNOn());
  }

  @YmMethodTest(name = "测试VPN连接是否存在1")
  public void testVpnExit1(){
    Intent intent = VpnService.prepare(getContext());
    if (intent != null) {
      showToastMessage("intent："+ ConvertObjectToString.toString(intent));

    } else {
      showToastMessage("intent为空");

    }
  }


  /**
   * 不一定适用 所有手机
   *
   * @return
   */
  private static boolean isVPNOn() {
    List<String> networkList = new ArrayList<>();
    try {
      for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
        if (networkInterface.isUp())
          networkList.add(networkInterface.getName());
      }
    } catch (Exception ex) {
      Log.e(YumoConfig.LOG_TAG, "isVpnUsing Network List didn't received");
    }
    return networkList.contains("tun0") || networkList.contains("ppp0");
  }

  @YmMethodTest(name = "打开VPN")
  public void openVpn(){
    startActivity(new Intent("android.net.vpn.SETTINGS"));
  }


  @YmMethodTest(name = "检测VPN链接")
  public void checkVPN() {
    ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    boolean connected =  cm.getNetworkInfo(ConnectivityManager.TYPE_VPN).isConnectedOrConnecting();
    showToastMessage("是否已连接："+connected);
  }

  public void testCheckVPN23(){
    boolean connected = checkVPN23(getContext());
    showToastMessage("是否已连接："+connected);
  }


  @TargetApi(Build.VERSION_CODES.M)
  public void testCheckTest(){
    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    Network activeNetwork = connectivityManager.getActiveNetwork();
    NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);
    boolean vpnInUse = caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
    showToastMessage("是否已连接："+vpnInUse);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) private boolean checkVPN23(Context context){
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    Network[] networks = cm.getAllNetworks();
    Log.i(YumoConfig.LOG_TAG, "Network count: " + networks.length);
    boolean result = false;
    for (int i = 0; i < networks.length; i++) {
      NetworkCapabilities caps = cm.getNetworkCapabilities(networks[i]);
      Log.i(YumoConfig.LOG_TAG, "Network " + i + ": " + networks[i].toString());
      Log.i(YumoConfig.LOG_TAG, "VPN transport is: " + caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN));
      Log.i(YumoConfig.LOG_TAG, "NOT_VPN capability is: " + caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN));
      if (caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
        result = true;
        break;
      }
    }
    return result;

  }
}
