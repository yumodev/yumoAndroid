package com.yumo.paho.mqtt;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


import com.yumo.paho.MqttManager;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttService extends Service {


    private final String TAG = "mqtt";
    /**
     * 客户端唯一标识
     */
    public static String MQTT_CLIENT_ID;


//    /**
//     * 代理服务器ip地址
//     */
//    private final String MQTT_BROKER_HOST = "tcp://192.168.2.57:8964";
//
//
//    /**
//     * 订阅标识
//     */
//    public static final String MQTT_TOPIC = "home/garden/fountain";
//
//    /**
//     * 用户名
//     */
//    public static final String USERNAME = "yumo";
//    /**
//     *  密码
//     */
//    public static final String PASSWORD = "123456";


    /**
     * 代理服务器ip地址
     */
    private final String MQTT_BROKER_HOST = "tcp://192.168.2.70:61613";

    /**
     * 订阅标识
     */
    public static final String MQTT_TOPIC = "jingwc";

    /**
     * 用户名
     */
    public static final String USERNAME = "admin";
    /**
     *  密码
     */
    public static final String PASSWORD = "password";

    private MqttClient mqttClient;

    public MqttService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MQTT_CLIENT_ID = MqttManager.getInstance().getClientId(getApplicationContext());

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * 连接mqtt
     */
    public void connect(){
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，
            // MemoryPersistence设置clientid的保存形式，默认为以内存保存
            mqttClient = new MqttClient(MQTT_BROKER_HOST,MQTT_CLIENT_ID,new MemoryPersistence());
            // 配置参数信息
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
            // 这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置用户名
            options.setUserName(USERNAME);
            // 设置密码
            options.setPassword(PASSWORD.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 连接
            mqttClient.connect(options);

//            , new IMqttActionListener(){
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    Log.e(TAG, "onSuccess ---> " + asyncActionToken.isComplete() );
//
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Log.e(TAG, "onFailure ---> " + asyncActionToken.isComplete() + "       exception--->" + exception.getMessage());
//
//                }
//            })

            // 订阅
            mqttClient.subscribe(MQTT_TOPIC);

            // 设置回调
            mqttClient.setCallback(new MqttCallback() {
                //连接丢失后，一般在这里面进行重连
                @Override
                public void connectionLost(Throwable throwable) {
                    Log.d(TAG,"connectionLost");
                }


                //subscribe后得到的消息会执行到这里面
                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    Log.d(TAG,"messageArrived"+mqttMessage.toString());
                }

                //publish后会执行到这里
                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    Log.d( TAG,"deliveryComplete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
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

    public boolean publicMessage(String msg){
        MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
       // mqttMessage.setQos();
        try {
            mqttClient.publish(MQTT_TOPIC, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private final Binder binder = new MyBinder();

    public class MyBinder extends Binder{

        public MqttService getService(){
            return MqttService.this;
        }
    }
}

