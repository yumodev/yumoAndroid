package com.yumodev.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.yumo.common.android.YmToastUtil;
import com.yumodev.test.MainActivity;
import com.yumodev.test.service.TestService;


/**
 * Created by yumo on 2018/8/3.
 */

public class ApkInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        YmToastUtil.showMessage(intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
//            File apk = new File(context.getFilesDir() + "/" + packageName + ".apk");
//            if (apk.exists()) {
//                apk.delete();
//            }

//            Intent intentRoot = new Intent(context, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intentRoot);

            context.startService(new Intent(context, TestService.class));
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();

            context.startService(new Intent(context, TestService.class));


//            Intent intentRoot = new Intent(context, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intentRoot);
        }
    }
}
