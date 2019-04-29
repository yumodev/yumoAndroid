package com.yumodev.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yumodev.ui.R;


/**
 * Created by yumodev on 12/8/15.
 */
public class MyDialogFragment extends DialogFragment {
    private final String LOG_TAG = "MyDialogFragment";
    public static int SHOW_TYPE_NORMAL = 0;
    public static int SHOW_TYPE_FULL = 1;
    public static int SHOW_TYPE_NO_TITLE = 2;
    public static int SHOW_TYPE_BOTTOM = 3;

    private int mShowType = SHOW_TYPE_NORMAL;

    public MyDialogFragment() {
        super();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateDialog");
        Dialog dialog = null;
        if (mShowType == SHOW_TYPE_BOTTOM){
            dialog = new Dialog(getContext(), R.style.common_dialog_bottom_style);
        }else if (mShowType == SHOW_TYPE_FULL){
            dialog = new Dialog(getContext(), R.style.common_dialog_full_style);
        }else {
            dialog = new Dialog(getContext(), R.style.common_dialog_style);
        }

        if (mShowType != SHOW_TYPE_NORMAL){
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(true);
        }

        dialog.setContentView(R.layout.dialog_fragment);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (mShowType == SHOW_TYPE_BOTTOM){
            window.getDecorView().setPadding(0,0,0,0);
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.BOTTOM;
            window.setWindowAnimations(R.style.dialogWindowBottomAnim);
        }else if (mShowType == SHOW_TYPE_FULL){
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        }
        window.setAttributes(lp);
        dialog.setTitle("testTitle");
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(LOG_TAG, "onActivityCreated");
        View view = null;
        if (getDialog() != null){
            view = getDialog().getWindow().getDecorView();
        }

        if (view != null){
            view .findViewById(R.id.ok_id).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setShowType(int showType){
        mShowType = showType;
    }


}
