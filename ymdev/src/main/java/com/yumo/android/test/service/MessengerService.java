package com.yumo.android.test.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.yumo.android.common.base.BaseService;
import com.yumo.common.io.ConvertObjectToString;
import com.yumo.common.log.Log;

/**
 * 测试Messenger 远程传递消息
 */
public class MessengerService extends BaseService {

    @Override
    protected String getTag() {
        return "MessengerService";
    }

    /**
     * 定义服务器端的MessageHandler 接收消息发送消息。
     */
    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.i("MessengerService", ConvertObjectToString.convertMessageToString(msg));

            /**
             * 向客户端发送消息
             */
            Message replyMessage = Message.obtain(null, 1);
            Bundle bundle = new Bundle();
            bundle.putString("reply:", "已收到客户端发过来的消息，稍后回复");
            replyMessage.setData(bundle);

            /**
             * 通过Message的replyTo，获取客户端的Messenger然后发送消息。
             */
            if (msg.replyTo != null) {
                try {
                    msg.replyTo.send(replyMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


}
