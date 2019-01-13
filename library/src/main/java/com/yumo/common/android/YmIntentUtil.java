package com.yumo.common.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by yumo on 2018/5/11.
 */

public class YmIntentUtil {

    public static String toString(Intent intent){
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
}
