package com.yumodev.process.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

/**
 * Created by yumo on 2018/5/4.
 */

public class DialogFacory {

    public static void showDialog(Context context, String message){
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setTitle("提示");
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.setMessage(message);
        dialog.setPositiveButton("关闭",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog=dialog.create();
        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//设定为系统级警告，关键
        mDialog.show();
    }
}
