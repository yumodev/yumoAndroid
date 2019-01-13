package com.yumo.android.common.widget.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by yumo on 15/11/20.
 */
public class YmFragment extends Fragment{
    private final String YmFgTAG = "FragmentDemo";

    private boolean mShowLog = false;

    /**
     *
     */
    public YmFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        showLog("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog("onCreate");
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        showLog("onCreateView");
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLog("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        showLog("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        showLog("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        showLog("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        showLog("onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showLog("onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        showLog("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showLog("onDetach()");
    }

    public void setShowLog(boolean show){
        mShowLog = show;
    }

    private void showLog(String msg){
        if (mShowLog){
            Log.d(YmFgTAG, msg);
        }
    }
}
