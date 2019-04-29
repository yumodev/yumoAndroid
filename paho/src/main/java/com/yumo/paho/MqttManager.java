package com.yumo.paho;

import android.content.Context;
import android.text.TextUtils;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.android.YmPrefManager;

import java.util.UUID;

public class MqttManager {

    private MqttManager(){}

    public static MqttManager getInstance(){
        return SingleHolder.mInstance;
    }

    public String getClientId(Context context){
        String clientId = YmPrefManager.getInstance().getValue(MqttDefine.PREF_KEY_CLIENT_ID, "");
        if (!TextUtils.isEmpty(clientId)){
            return clientId;
        }

        String imei = YmAppUtil.getImei(context);
        if (TextUtils.isEmpty(imei)){
            return UUID.randomUUID().toString();
        }
        return imei;
    }

    public String getUserName(){
        return YmPrefManager.getInstance().getValue(MqttDefine.PREF_KEY_USER_NAME, "yumo");
    }

    public void setUserName(String userName){
        YmPrefManager.getInstance().setValue(MqttDefine.PREF_KEY_USER_NAME, userName, false);
    }

    public String getUserPassword(){
        return YmPrefManager.getInstance().getValue(MqttDefine.PREF_KEY_USER_PASSWORD, "123456");
    }

    public void setUserPassword(String userPassword){
        YmPrefManager.getInstance().setValue(MqttDefine.PREF_KEY_USER_PASSWORD, userPassword, false);
    }

    public void setClientId(String clientId){
        YmPrefManager.getInstance().setValue(MqttDefine.PREF_KEY_CLIENT_ID, clientId, false);
    }

    public String getMqttHost(){
        return YmPrefManager.getInstance().getValue(MqttDefine.PREF_KEY_MQTT_HOST, "");
    }

    public void setMqttHost(String mqttHost){
        YmPrefManager.getInstance().setValue(MqttDefine.PREF_KEY_MQTT_HOST, mqttHost, false);
    }


    public String getMqttSubscribe(){
        return YmPrefManager.getInstance().getValue(MqttDefine.PREF_KEY_MQTT_SUBSCRIBE, "subscribe");
    }

    public void setMqttSubscribe(String userName){
        YmPrefManager.getInstance().setValue(MqttDefine.PREF_KEY_MQTT_SUBSCRIBE, userName, false);
    }

    private static class SingleHolder{
        private final static MqttManager mInstance = new MqttManager();
    }
}
