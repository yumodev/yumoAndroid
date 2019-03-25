package com.yumo.android.test.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yumo.common.io.ConvertObjectToString;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

public class TestMessengerView extends YmTestFragment {

    private Messenger messenger;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /**
             * 获取服务器端的Messenger
             */
            messenger = new Messenger(service);
            /**
             * 发送消息。
             */
            sendMessage("连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 定义客户端的Messenger，然后将其负值给Message的replyTo字段。
     */
    private Messenger replyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.i("MessengerService", ConvertObjectToString.convertMessageToString(msg));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this.getActivity(), MessengerService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        getActivity().unbindService(mConnection);
        super.onDestroy();
    }

    private void sendMessage(String msg){
        try {
            Bundle bundle = new Bundle();
            bundle.putString("test:", msg);
            Message message = Message.obtain(null, 0);
            message.setData(bundle);
            message.replyTo = replyMessenger;
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void testMessge(){
        sendMessage("发送测试消息");
    }
}
