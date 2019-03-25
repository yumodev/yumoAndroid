package com.yumodev.process.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.yumo.common.util.YmDateUtil;
import com.yumodev.process.MainActivity;
import com.yumodev.process.util.DialogFacory;
import com.yumodev.process.util.TestAppUtil;

/**
 * Created by yumo on 2018/4/19.
 */
public class YmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DialogFacory.showDialog(context, "开机广告");
        XLog.i(YmDateUtil.getStrTime()+" YmBootReceiver "+intent.getAction());
        TestAppUtil.openService(context, LocalService.class.getName());
        BackgroudAlarm.startAlarm(context);

        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
