package com.yumodev.ui.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.yumo.common.android.YmContext;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 15/11/22.
 */
public class AlertDialogTestView extends YmTestFragment {

    public final String TAG = "AlertDialogTestView";

    public void testShowAlertDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("alertDialog")
                .setMessage("message")
                .setPositiveButton("cancel", null)
                .setNegativeButton("ok", null)
                .show();

    }

    public void testDialogFragment() {
        MyDialogFragment dialog = new MyDialogFragment();
        showDialogFragment(dialog, "testDialog");
    }

    /**
     * ok cancel 两种对话框
     * yumo
     * void
     * 2015-1-30
     */
    public void testDialogOkCancel() {
        new AlertDialog.Builder(getActivity())
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("ok_cancel")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void testDialogOkCancelThird() {
        new AlertDialog.Builder(getActivity())
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("three button")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNeutralButton("third", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * 列表弹出框
     * yumo
     * void
     * 2015-1-30
     */
    public void testDialogList() {
        String[] list = new String[10];
        for (int n = 0; n < 10; n++) {
            list[n] = "项编号：" + n;
        }
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("dialog_list")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "list:" + which, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();

    }

    /**
     * 编辑弹出框
     * yumo
     * void
     * 2015-1-30
     */
    public void testDialogEdit() {
        final EditText editTest = new EditText(getContext());
        new AlertDialog.Builder(getActivity())
                .setTitle("dialog edit")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editTest)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "positiveButton:" + editTest.getText().toString());
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "positiveButton:" + editTest.getText().toString());
                    }
                })
                .create()
                .show();
    }

    public void testDialogSingleChoice() {
        String[] list = new String[10];
        for (int n = 0; n < 10; n++) {
            list[n] = "项编号：" + n;
        }
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("dialog_list")
                .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "list:" + which, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();

    }

    public void testDialogMulitChoice() {
        String[] list = new String[10];
        for (int n = 0; n < 10; n++) {
            list[n] = "项编号：" + n;
        }

        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("dialog_list")
                .setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Log.d(TAG, "onMultiChoiceClickListener:" + which);
                        Toast.makeText(getActivity(), "list:", Toast.LENGTH_SHORT).show();
                    }
                })

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();

    }

    /**
     * 弹出系统对话框
     */
    public void testShowSystemDialog(){
       AlertDialog.Builder builder =  new AlertDialog.Builder(getContext().getApplicationContext())
                .setTitle("alertDialog")
                .setMessage("message")
                .setPositiveButton("cancel", null)
                .setNegativeButton("ok", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

}
