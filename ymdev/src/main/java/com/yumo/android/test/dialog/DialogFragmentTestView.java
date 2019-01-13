package com.yumo.android.test.dialog;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 15/11/22.
 */
public class DialogFragmentTestView extends YmTestFragment {
    public final String TAG = "DialogFragmentTestView";

    public void testDialogFragment() {
        MyDialogFragment dialog = new MyDialogFragment();
        showDialogFragment(dialog, "testDialog");
    }

    public void testFull() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setShowType(MyDialogFragment.SHOW_TYPE_FULL);
        showDialogFragment(dialog, "testDialog");
    }

    public void testBottom() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setShowType(MyDialogFragment.SHOW_TYPE_BOTTOM);
        showDialogFragment(dialog, "testDialog");
    }

    public void testNoTitle() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setShowType(MyDialogFragment.SHOW_TYPE_NO_TITLE);
        showDialogFragment(dialog, "testDialog");
    }
}
