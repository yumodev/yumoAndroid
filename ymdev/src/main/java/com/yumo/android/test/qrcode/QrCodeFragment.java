package com.yumo.android.test.qrcode;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.yumo.android.R;
import com.yumo.common.log.Log;

/**
 * Created by yumodev on 17/11/18.
 *
 * 二维码扫描
 */

public class QrCodeFragment extends Fragment implements SurfaceHolder.Callback{
    private final String LOG_TAG = Log.LIB_TAG;
    private boolean mSurfaceCreated = false;
    private SurfaceView mSurfaceView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceCreated = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.qrcode_fragment, null);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceCreated = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void initView(View rootView){
        mSurfaceView = rootView.findViewById(R.id.preview_view);
    }
}
