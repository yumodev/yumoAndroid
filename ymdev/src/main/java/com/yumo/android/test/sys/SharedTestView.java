package com.yumo.android.test.sys;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 4/2/16.
 */
public class SharedTestView extends YmTestFragment {

    public void testSharedSystem(){
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, "aciton send sys");
        send.putExtra(Intent.EXTRA_SUBJECT, "title");
        try {
            startActivity(Intent.createChooser(send, "action send sys"));
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

    public void testSharedSystemWithUrl(){
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, "http://yumodev.com/test/test_scheme.html");
        send.putExtra(Intent.EXTRA_SUBJECT, "test");
        try {
            startActivity(Intent.createChooser(send, "action send sys"));
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

    public void testSharedCustom(){
        showFragment(new SharedCustomFragment());
    }

    public void testShareWeixin(){
        try {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            if (!(getContext() instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            getContext().getApplicationContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
