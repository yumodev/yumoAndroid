package com.yumo.android.test.sys;

import android.content.Intent;
import android.net.VpnService;
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
}
