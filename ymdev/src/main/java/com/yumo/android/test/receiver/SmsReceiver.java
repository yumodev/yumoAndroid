/**
 * SmsReceiver.java
 * yumodev
 * 2015-3-15
 */
package com.yumo.android.test.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * yumodev
 */
public class SmsReceiver extends BroadcastReceiver {

    /**
     * 短信监听
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == "action.provider.Telephony.SMS_RECEIVED") {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SmsMessage smsMsg = null;
                Object[] objects = (Object[]) bundle.get("pdns");
                for (Object obj : objects) {
                    smsMsg = SmsMessage.createFromPdu((byte[]) obj);
                    Date date = new Date(smsMsg.getTimestampMillis());
                    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String receiveTime = simpleFormat.format(date);
                    Log.d("SmsReceiver", "number:" + smsMsg.getOriginatingAddress() + "receiveTime:" + receiveTime + " body:" + smsMsg.getDisplayMessageBody());
                }
            }
        }
    }

}
