package com.yumo.paho.mqtt;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

import com.yumo.paho.MqttManager;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttAndroidService extends Service {


    private final String TAG = "mqtt";


    private MqttAndroidClient mqttClient;
    private YmMqttCallbackListener mCallbackListener;

    public MqttAndroidService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setCallbackListener(YmMqttCallbackListener listener){
        mCallbackListener = listener;
    }

    public void connect(){
        mqttClient = new MqttAndroidClient(getApplicationContext(), MqttManager.getInstance().getMqttHost(), MqttManager.getInstance().getClientId(getApplicationContext()));

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        // 配置MQTT连接
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(MqttManager.getInstance().getUserName());
        mqttConnectOptions.setPassword(MqttManager.getInstance().getUserPassword().toCharArray());
        mqttConnectOptions.setConnectionTimeout(10);  //超时时间
        mqttConnectOptions.setKeepAliveInterval(60); //心跳时间,单位秒
        mqttConnectOptions.setAutomaticReconnect(true);//自动重连
        mqttConnectOptions.setMaxInflight(10);//允许同时发送几条消息（未收到broker确认信息）
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);//选择MQTT版本


        try {
            mqttClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e(TAG, "onSuccess ---> " + asyncActionToken.isComplete() );
                    if (mCallbackListener != null){
                        mCallbackListener.onMessage("onSuccess ---> " + asyncActionToken.isComplete() );
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "onFailure ---> " + asyncActionToken.isComplete() + "       exception--->" + exception.getMessage());
                    if (mCallbackListener != null){
                        mCallbackListener.onMessage("onFailure ---> " + asyncActionToken.isComplete() + "       exception--->" + exception.getMessage());
                    }
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            Log.e(TAG, "       exception--->" +e.getMessage());
            if (mCallbackListener != null){
                mCallbackListener.onMessage("connect:"+e.getMessage());
            }
        }


        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.e(TAG, "reconnect ---> " + reconnect + "       serverURI--->" + serverURI);
                //subscribeToTopic();
                if (mCallbackListener != null){
                    mCallbackListener.onMessage("reconnect ---> " + reconnect + "       serverURI--->" + serverURI);
                }
            }

            //连接丢失后，一般在这里面进行重连
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "cause ---> " + cause);
                if (mCallbackListener != null){
                    mCallbackListener.onMessage("connectionLost cause ---> " + cause);
                }
            }

            //subscribe后得到的消息会执行到这里面
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.e(TAG, "topic ---> " + topic + "       message--->" + message);
                //startNotification(message);
                if (mCallbackListener != null){
                    mCallbackListener.onMessage("topic ---> " + topic + "       message--->" + message);
                }
            }

            //publish后会执行到这里
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.e(TAG, "token ---> " + token);
                if (mCallbackListener != null){
                    try {
                        mCallbackListener.onMessage("deliveryComplete:"+token+" "+token.getMessage().toString());
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    /**
     * 断开连接
     */
    public void disconnect(){
        if(mqttClient != null){
            if(mqttClient.isConnected()){
                try {
                    mqttClient.disconnect();
                    mqttClient = null;
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断是否连接
     * @return
     */
    public boolean isConnect(){
        if (mqttClient == null){
            return false;
        }
        return mqttClient.isConnected();
    }

    /**
     * 发布消息
     * @param msg
     * @return
     */
    public boolean publicMessage(String subscribe, String msg, IMqttActionListener listener){
        MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
        mqttMessage.setQos(1);
        try {
            IMqttDeliveryToken iMqttDeliveryToken = mqttClient.publish(subscribe, mqttMessage, null, listener);
            if (iMqttDeliveryToken != null){
                MqttMessage mqttMessage1 = iMqttDeliveryToken.getMessage();
            }
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 订阅消息
     * @param subcribeTopic
     * @return
     */
    public void subscribeTopic(String subcribeTopic, final IMqttActionListener listener){
        try {
            mqttClient.subscribe(subcribeTopic, 1, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e(TAG, "onSuccess---> " + asyncActionToken);
                    if (listener != null){
                        listener.onSuccess(asyncActionToken);
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "onFailure ---> " + exception);
                    if (listener != null){
                        listener.onFailure(asyncActionToken, exception);
                    }
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, "subscribeToTopic is error");
            e.printStackTrace();
            if (listener != null){
                listener.onFailure(null, e);
            }
        }
    }


    private final Binder binder = new MyBinder();

    public class MyBinder extends Binder{

        public MqttAndroidService getService(){
            return MqttAndroidService.this;
        }
    }


    public static interface YmMqttCallbackListener{
        void onMessage(String message);
    }
}

