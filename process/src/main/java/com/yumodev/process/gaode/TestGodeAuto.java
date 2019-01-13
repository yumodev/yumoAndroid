package com.yumodev.process.gaode;

import android.content.Intent;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/6/21.
 */

public class TestGodeAuto extends YmTestFragment {

    /**
     * 熄火
     */
    public void testAccOff(){
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10018);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    /**
     * 进入主图
     */
    public void testOpenAuto(){
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10034);
        intent.putExtra("SOURCE_APP","Third App");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setClassName("com.autonavi.amapauto","com.autonavi.amapauto.adapter.internal.AmapAutoBroadcastReceiver");

        getContext().sendBroadcast(intent);
    }

    /**
     * 退出主图
     */
    public void testExitAuto(){
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10021);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setClassName("com.autonavi.amapauto","com.autonavi.amapauto.adapter.internal.AmapAutoBroadcastReceiver");

        getContext().sendBroadcast(intent);
    }

    /***
     * 退到后台
     */
    public void testHomeAuto(){
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10031);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setClassName("com.autonavi.amapauto","com.autonavi.amapauto.adapter.internal.AmapAutoBroadcastReceiver");

        getContext().sendBroadcast(intent);
    }
}
