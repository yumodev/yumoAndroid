package com.yumo.android.test.net.websocket;

import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class TestWebSocket extends YmTestFragment {
    private final String LOG_TAG = "websocket";

    private WebSocket mSocket;
    //private String wsurl = "ws://echo.websocket.org";
    private String wsurl = "ws://zebra-zebra-uic.icongtai.com/dcb/websocket/adjdsjfskhjskdfhskdo8899#BYD";

    public void testStart(){
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Request request = new Request.Builder().url(wsurl).build();
        EchoWebSocketListener1 socketListener = new EchoWebSocketListener1();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }


    public void testSenMessage(){
        mSocket.send("adjdsjfskhjskdfhskdo8899#BYD");
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            String openid = "1";
            //连接成功后，发送登录信息
            String message = "{\"type\":\"login\",\"user_id\":\""+openid+"\"}";
            mSocket.send(message);
            output("连接成功！");

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            output("receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            output("receive text:" + text);
            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
            final String message = "{\"type\":\"heartbeat\",\"user_id\":\"heartbeat\"}";
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSocket.send(message);
                }
            },25000);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            output("closed:" + reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            output("closing:" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            output("failure:" + t.getMessage());
        }
    }


    private final class EchoWebSocketListener1 extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            output("onOpen:"+printResponce(response));

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            output("receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            output("receive text:" + text);

        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            output("closed:" + reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            output("closing:" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            output("failure:" + t.getMessage());
        }
    }

    private void output(final String text) {
        Log.i(LOG_TAG, text);
    }

    private String printResponce(Response response){
        if (response == null){
            return " response is nulll";
        }

        if (response.isSuccessful()){
            return " response is failed :"+response.message()+" "+response.code();
        }else{
            return response.body().toString();
        }
    }

}
