package com.yumodev.process.test;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.util.TestAppUtil;

/**
 * Created by yumo on 2018/6/7.
 * "type":"ROM_SETTING","subType":"act_cate","act":"miui.intent.action.OP_AUTO_START","category":"android.intent.category.DEFAULT
 */

public class TestMiui extends YmTestFragment {

    //com.miui.powerkeeper/.ui.HiddenAppsContainerManagementActivity
    public void testHiddenAppsContainerManagementActivity(){
        TestAppUtil.openActivity(getContext(), "com.miui.powerkeeper/.ui.HiddenAppsContainerManagementActivity");
    }

    public void testHiddenAppsContainerManagementActivity1(){
        TestAppUtil.openActivity(getContext(), "com.miui.powerkeeper", "HiddenAppsContainerManagementActivity");
    }


    public void testHiddenAppsContainerManagementActivity2(){
        TestAppUtil.openActivity(getContext(), "com.miui.securitycenter", "HiddenAppsContainerManagementActivity");
    }

    //com.miui.securitycenter/com.miui.permcenter.permissions.PermissionsEditorActivity
    public void testPermissionsEditorActivity(){
        TestAppUtil.openActivity(getContext(), "com.miui.securitycenter/com.miui.permcenter.permissions.PermissionsEditorActivity");
    }

    public void testPermissionsEditorActivity1(){
        //TestAppUtil.openActivity(getContext(), "com.miui.securitycenter", "PermissionsEditorActivity");
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR_PRIVATE");
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        intent.addCategory("android.intent.category.DEFAULT");
        getActivity().startActivity(intent);

    }

    //com.miui.securitycenter/com.miui.appmanager.AppManagerMainActivity
    public void testAppPermissionsTabActivity(){
        TestAppUtil.openActivity(getContext(), "com.miui.securitycenter/com.miui.permcenter.permissions.AppPermissionsTabActivity");
    }

    public void testAppManagerMainActivity(){
        TestAppUtil.openActivity(getContext(), "com.miui.securitycenter/com.miui.appmanager.AppManagerMainActivity");
    }


    /**
     * 打开清理任务失败。
     */
    public void testLockAppManagerActivity(){
        //com.miui.securitycenter/com.miui.optimizemanage.memoryclean.LockAppManageActivity
        TestAppUtil.openActivity(getContext(), "com.miui.securitycenter/com.miui.optimizemanage.memoryclean.LockAppManageActivity");
    }

    /**
     * 打开清理任务失败
     */
    // com.miui.securitycenter/com.miui.optimizemanage.settings.SettingsActivity
    public void testOptimizemanage(){
        TestAppUtil.openActivity(getContext(), " com.miui.securitycenter/com.miui.optimizemanage.settings.SettingsActivity");
    }
}
