package com.yumodev.flurry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yumo on 2018/4/28.
 */

public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        Log.e("Flurry", "TP PUSH "+ intent.getAction());

        if (intent.getAction().equals("com.yumodev.process.flurry")){
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }
}
