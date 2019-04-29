package com.yumodev.glide.test;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.yumodev.glide.Define;
import com.yumodev.glide.GlideApp;
import com.yumodev.glide.R;

public class TestDialogFragment extends DialogFragment {
    private ImageView mImageView;
    private boolean mUseGlide = false;
    private int mLastOri;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        mImageView = new ImageView(getContext());
        getDialog().setContentView(mImageView);

        Bundle bundle = getArguments();
        ImageView.ScaleType scaleType = Enum.valueOf(ImageView.ScaleType.class, bundle.getString("scaletype"));
        mImageView.setScaleType(scaleType);
        mUseGlide = bundle.getBoolean("useglide", false);
        mLastOri = getResources().getConfiguration().orientation;

        if (mImageView != null){
            if (mUseGlide){
                GlideApp.with(this).load(getDrawbleRes()).into(mImageView);
            }else{
                mImageView.setImageResource(getDrawbleRes());
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mLastOri != newConfig.orientation){
            mLastOri = newConfig.orientation;

            if (mImageView != null){
                if (mUseGlide){
                    GlideApp.with(this).load(getDrawbleRes()).into(mImageView);
                }else{
                    mImageView.setImageResource(getDrawbleRes());
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("width:"+mImageView.getWidth());
            sb.append(" height:"+mImageView.getHeight());
            sb.append(" test:"+R.drawable.test);
            Log.i(Define.LOG_TAG, sb.toString());
        }
    }

    private int getDrawbleRes(){
        if (mLastOri == Configuration.ORIENTATION_PORTRAIT){
            return R.drawable.test;
        }
        return R.drawable.test_land;
    }
}
