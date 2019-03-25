package com.yumo.common.io;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;

import com.yumo.common.log.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ConvertObjectToString {

    public static String toString(Intent intent){
        if (intent == null){
            return " intent is null ";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(intent.getAction()).append(" ");

        Bundle bundle = intent.getExtras();
        if (bundle != null){
            Set<String> sets = bundle.keySet();
            for (String key : sets){
                if (bundle.get(key) instanceof Integer){
                    sb.append(key).append(":").append(bundle.getInt(key)).append("\n");
                }else if (bundle.get(key) instanceof ArrayList){
                    sb.append(key).append(":").append(Arrays.toString(bundle.getIntegerArrayList(key).toArray())).append("\n");
                }else if (bundle.get(key) instanceof Parcelable) {
                    sb.append(key).append(":").append(bundle.getParcelable(key).toString()).append("\n");
                }else if (bundle.get(key) instanceof Boolean){
                    sb.append(key).append(":").append(bundle.getBoolean(key)).append("\n");
                }else{
                    try {
                        sb.append(key).append(":").append(String.valueOf(bundle.get(key))).append("\n");
                    }catch (Exception e){
                        e.printStackTrace();
                        sb.append(key).append(":").append(bundle.get(key).getClass().getSimpleName());
                    }

                }
            }
        }

        return sb.toString();
    }

    public static String convertMessageToString(Message message){
        if (message == null){
            return " message is null ";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(message.toString()).append("\n");
        if (message.getData() != null){
            sb.append(convertObjectToString(message.getData()));
        }
        return sb.toString();
    }

    public static String convertObjectToString(Bundle bundle){
        if (bundle == null){
           return " bundle is null ";
        }

        StringBuilder sb = new StringBuilder();

        for (String key: bundle.keySet()) {
            sb.append("Key=").append( key ).append(", content=");
            if (bundle.getString(key) != null){
                sb.append(bundle.getString(key)).append("\n");
            }
        }

        return sb.toString();
    }
}
