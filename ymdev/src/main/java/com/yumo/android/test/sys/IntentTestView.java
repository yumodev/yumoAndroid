/**
 * IntentTestView.java
 * yumo
 * 2015-1-23
 * 这是一个常用的Intent类，主要用这些Intent来测试打开一些系统功能，和自定义的一些Intent。
 */
package com.yumo.android.test.sys;

import com.yumo.demo.view.YmTestFragment;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

/**
 * yumo
 */
public class IntentTestView extends YmTestFragment {
    private final String TAG = IntentTestView.class.getSimpleName() + "  ";

    public void testOpenUrl(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    /**
     * 调用拨打电话页面
     */
    public void testActionDial(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:101"));
        startActivity(intent);
    }

    /**
     * 直接拨打电话
     */
    public void testDireactDial(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:101"));
        startActivity(intent);
    }

    /**
     * 打开地图
     */
    public void testMap(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:39.945,106.404")));
    }

    /**
     * 调用mail页面
     */
    public void testMailPage(){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:duzi_tingyu@163.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "测试邮件");
        intent.putExtra(Intent.EXTRA_TEXT, " 这是测试哟件，哈哈");
        startActivity(intent);
    }

    public void testSendMail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String[] email = {"duzi_tingyu@163.com"};
        String[] cc = {"disheng54@163.com"};
        String[] bcc = {"disheng@163.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_CC, cc);
        intent.putExtra(Intent.EXTRA_BCC, bcc);
        intent.putExtra(Intent.EXTRA_SUBJECT, "测试邮件");
        intent.putExtra(Intent.EXTRA_TEXT, " 这是测试哟件，哈哈");

        //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/a.jpg"));
        //intent.setType("image/*");
        intent.setType("message/rfc882");
        Intent.createChooser(intent, "Choose Email Client");

        startActivity(intent);
    }

    /**
     * 发短信
     */
    public void testSms(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:18910648612"));
        intent.putExtra("sms_body", "吃饭了吗");
        intent.setType("vnd.android-dir/mms-sms");
        startActivity(intent);
    }

    /**
     * 自定义电话号码，发送短信
     */
    public void testSmsTo(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:10086"));
        intent.putExtra("sms_body", "吃饭了吗");
        startActivity(intent);
    }

    /**
     * 卸载APP
     */
    public void testUninstallApp(){
        startActivity(new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", "com.yumo.android", null)));
    }


    public void testStartApp(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //air.tv.douyu.android/tv.douyu.view.activity.MainActivity
        ComponentName cn = new ComponentName("air.tv.douyu.android","tv.douyu.view.activity.MainActivity");
        intent.setComponent(cn);
        startActivity(intent);
    }
}
