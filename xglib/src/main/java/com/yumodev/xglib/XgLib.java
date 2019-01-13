package com.yumodev.xglib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;

/**
 * Created by yumo on 2018/4/27.
 */

public class XgLib {
    public static void init(Context context){
        //清除通知栏消息
        //XGPushManager.cancelAllNotifaction();

        //代码内动态注册access ID
        //XGPushConfig.setAccessId(this,2100250470);

        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(context, true);
        XGPushConfig.getToken(context);
//        //注册数据更新监听器
//        updateListViewReceiver = new MsgReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
//        context.registerReceiver(updateListViewReceiver, intentFilter);
//        // 1.获取设备Token
//        Handler handler = new HandlerExtension(context);
//        m = handler.obtainMessage();
        /*
        注册信鸽服务的接口
        如果仅仅需要发推送消息调用这段代码即可
        */
        XGPushManager.registerPush(context,
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.w(Constants.LogTag, "+++ register push sucess. token:" + data + "flag" + flag);

//                        m.obj = "+++ register push sucess. token:" + data;
//                        m.sendToTarget();
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.w(Constants.LogTag,
                                "+++ register push fail. token:" + data
                                        + ", errCode:" + errCode + ",msg:"
                                        + msg);
//                        m.obj = "+++ register push fail. token:" + data
//                                + ", errCode:" + errCode + ",msg:" + msg;
//                        m.sendToTarget();
                    }
                });

        // 获取token
        XGPushConfig.getToken(context);

        //反注册代码，线上版本不能调用
        //XGPushManager.unregisterPush(this);
    }

//    public static class MsgReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // TODO Auto-generated method stub
//            allRecorders = notificationService.getCount();
//            getNotificationswithouthint(id);
//        }
//    }
}
