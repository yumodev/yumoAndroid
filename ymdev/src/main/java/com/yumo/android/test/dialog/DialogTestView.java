package com.yumo.android.test.dialog;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/3/26.
 */

public class DialogTestView extends YmTestFragment {

    public void testshowSoftInputView(){
        SoftInputViewDialog dialog = new SoftInputViewDialog(getActivity(), R.style.common_dialog_full_style);
        dialog.show();
    }
}
