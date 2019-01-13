package com.yumodev.process.test;

import android.content.ComponentName;
import android.content.Intent;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/6/28.
 *
 * com.autonavi.amapautolite 高德地图云镜版本
 */

public class TestBroadCat extends YmTestFragment {

    public void testOpenAcc(){
        Intent intent = new Intent();
        intent.setAction("com.stcloud.drive.EVT_ACC_STATUS");
        intent.putExtra("acc", "1");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    public void testCloaseAcc(){
        Intent intent = new Intent();
        intent.setAction("com.stcloud.drive.EVT_ACC_STATUS");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("acc", "0");
        getContext().sendBroadcast(intent);
    }

    public void testStartCarLocationService(){
        Intent intent = new Intent();
        intent.setAction("zebra.drive.locationService");
        intent.setPackage("com.icongtai.zebra.car");

        getActivity().startService(intent);
    }

    public void testStartDemoLocationService(){
        Intent intent = new Intent();
        intent.setAction("zebra.drive.locationService");
        intent.setPackage("com.icongtai.zebra.sdk.demo");

        getActivity().startService(intent);
    }


    public void testOpenMaiguAcc(){
        Intent intent = new Intent("com.mapgoo.accstatus");
        intent.putExtra("status", "accon");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);

    }

    public void testCloaseMaiguAcc(){
        Intent intent = new Intent("com.mapgoo.accstatus");
        intent.putExtra("status", "accoff");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    public void testGetMaiguAcc(){
        Intent intent = new Intent("com.mapgoo.getaccstatus");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    public void testOpenZebraService(){
        Intent intent = new Intent("com.icongtai.zebra.car");
        intent.putExtra("KEY_TYPE", 0);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    public void testStartDriving(){
        Intent intent = new Intent("com.icongtai.zebra.car");
        intent.putExtra("KEY_TYPE", 1);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    public void testStopDriving(){
        Intent intent = new Intent("com.icongtai.zebra.car");
        intent.putExtra("KEY_TYPE", 2);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }


    public void testOpenCollision(){
        Intent intent = new Intent("com.icongtai.zebra.collision");
        intent.putExtra("TYPE", 0);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    /**
     * 启动服务
     */
    public void testStart2CollsionService(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.icongtai.zebra.collision", "com.zebra.collision.core.service.WorkerService"));
        getActivity().startService(intent);
    }

    /**
     * 启动服务
     */
    public void testStart3CollsionService(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.icongtai.zebra.collision", "com.zebra.collision.core.service.WorkerService"));
        getActivity().startService(intent);
        testStartCheck();
    }

    /**
     * 开始检测
     */
    public void testStartCheck(){
        Intent intent = new Intent("com.icongtai.zebra.collision");
        intent.putExtra("TYPE", 0);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    /**
     * 结束检测
     */
    public void testStopCheck(){
        Intent intent = new Intent("com.icongtai.zebra.collision");
        intent.putExtra("TYPE", 1);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getContext().sendBroadcast(intent);
    }

    public void testStartCollsionService(){
        Intent intent = new Intent();
        intent.setAction("com.zebra.location.core.service.WorkerService");
        intent.setPackage("com.icongtai.zebra.collision");
        getActivity().startService(intent);
    }

    public void testStart1CollsionService(){
        Intent intent = new Intent();
        intent.setAction("com.zebra.collision.core.service.WorkerService");
        intent.setPackage("com.icongtai.zebra.collision");

        //com.zebra.collision.core.service.WorkerService

        getActivity().startService(intent);
    }


}
