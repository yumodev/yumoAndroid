package com.yumodev.ui.dialog;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.ui.R;

/**
 * Created by yumodev on 17/3/26.
 */

public class DialogTestView extends YmTestFragment {

    public void testshowSoftInputView(){
        SoftInputViewDialog dialog = new SoftInputViewDialog(getActivity(), R.style.common_dialog_full_style);
        dialog.show();
    }

    public void testDialogFragment() {
        MyDialogFragment dialog = new MyDialogFragment();
        showDialogFragment(dialog, "testDialog");
    }

    public void testFullDialogFragemnt() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setShowType(MyDialogFragment.SHOW_TYPE_FULL);
        showDialogFragment(dialog, "testDialog");
    }

    public void testBottomDialogFragment() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setShowType(MyDialogFragment.SHOW_TYPE_BOTTOM);
        showDialogFragment(dialog, "testDialog");
    }

    public void testNoTitleDialogFragment() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setShowType(MyDialogFragment.SHOW_TYPE_NO_TITLE);
        showDialogFragment(dialog, "testDialog");
    }
}
