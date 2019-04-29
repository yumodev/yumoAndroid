package com.yumodev.glide;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.glide.test.TestDialogFragment;

/**
 * ScaleType的判断
 */
public class TestScaleType extends YmTestFragment {
    private final String LOG_TAG = "Glide";

    private ImageView mImageView = null;
    private boolean mUseGlide = true;
    private boolean mUserDialogFragment = true;
    private int mLastOri;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLastOri = getResources().getConfiguration().orientation;
    }

    public void testUseGlide(){
        mUseGlide = true;
    }

    public void testUseUnGlide(){
        mUseGlide = false;
    }

    public void testUseDialogFragment(){
        mUserDialogFragment = true;
    }

    public void testUseUnDialogFragment(){
        mUserDialogFragment = false;
    }
    public void testShowDefalutImageView(){
        showScaleType(ImageView.ScaleType.MATRIX);
    }

    public void testFixXyImageView(){
       showScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void testFixStartImageView(){
        showScaleType(ImageView.ScaleType.FIT_START);
    }

    public void testFieCenterImageView(){
        showScaleType(ImageView.ScaleType.FIT_CENTER);
    }


    public void testFitEndImageView(){
        showScaleType(ImageView.ScaleType.FIT_END);
    }

    public void testCenterImageView(){
        showScaleType(ImageView.ScaleType.CENTER);
    }


    public void testCenterCropImageView(){
        showScaleType(ImageView.ScaleType.CENTER_CROP);
    }


    public void testCneterInsideImageView(){
        showScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }



    private void showScaleType(ImageView.ScaleType scaleType){
        if (!mUserDialogFragment){
            mImageView = new ImageView(getContext());
            mImageView.setScaleType(scaleType);
            showTestView(mImageView);

            mImageView.post(new Runnable() {
                @Override
                public void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append("width:"+mImageView.getWidth());
                    sb.append(" height:"+mImageView.getHeight());
                    sb.append(" test:"+R.drawable.test);
                    Log.i(LOG_TAG, sb.toString());
                }
            });

            if (mUseGlide){
                GlideApp.with(this).load(R.drawable.test).into(mImageView);
            }else{
                mImageView.setImageResource(R.drawable.test);
            }
        }else{
            TestDialogFragment fragment = new TestDialogFragment();
            Bundle bundle  = new Bundle();
            bundle.putString("scaletype", scaleType.name());
            bundle.putBoolean("useglide", mUseGlide);
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction().add(fragment, "dialog").commitAllowingStateLoss();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mLastOri != newConfig.orientation){
            mLastOri = newConfig.orientation;

            if (mImageView != null){
                if (mUseGlide){
                    GlideApp.with(this).load(R.drawable.test).into(mImageView);
                }else{
                    mImageView.setImageResource(R.drawable.test);
                }

                StringBuilder sb = new StringBuilder();
                sb.append("width:"+mImageView.getWidth());
                sb.append(" height:"+mImageView.getHeight());
                sb.append(" test:"+R.drawable.test);
                Log.i(LOG_TAG, sb.toString());
            }
        }
    }
}
