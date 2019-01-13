package com.yumo.android.test.activity;

import android.content.Intent;

import com.yumo.android.test.activity.dialog.DialogAppcompatActivity;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/3/25.
 */

public class ActivityTestFragment extends YmTestFragment {

    /**
     * 打开对话框样式的APP
     */
    public void showDialogAppCompactActivity(){
        getActivity().startActivity(new Intent(getActivity(), DialogAppcompatActivity.class));
    }

}
