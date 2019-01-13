package com.yumo.ui.fragment;

import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by yumodev on 9/9/16.
 */
public class YmDialogFragment extends DialogFragment {

    protected RefreshDataListener mRefreshDataListener = null;

    public void addRefreshDataListener(RefreshDataListener listener){
        mRefreshDataListener = listener;
    }

    public void showThreadToast(final @StringRes int resId){
        if (isAdded()){
            String text = getString(resId);
            showThreadToast(text);
        }
    }

    public void showThreadToast(final String text){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isAdded()){
                    return;
                }

                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshDataAfterDismiss(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRefreshDataListener != null){
                    mRefreshDataListener.refreshDataAfterDismiss();
                }
            }
        });
    }

    public interface RefreshDataListener{
        void refreshDataAfterDismiss();
    }
}
