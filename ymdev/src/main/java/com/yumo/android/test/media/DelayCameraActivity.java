/**
 * DelayCameraActivity.java
 * yumodev
 * 2014-12-26
 * 延时拍照
 */
package com.yumo.android.test.media;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yumo.android.R;
import com.yumo.common.log.Log;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.functions.Consumer;

/**
 * yumodev
 * 延迟拍照
 */
public class DelayCameraActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback, Camera.PictureCallback {
    private final String TAG = DelayCameraActivity.class.getSimpleName() + " " + Log.LIB_TAG;

    private SurfaceView mCameraView = null;
    private SurfaceHolder mCameraHolder = null;
    private Camera mCamera = null;
    private boolean mSurfaceCreated = false;
    private TextView mCountDownText = null;

    private int mCountTime = 10;
    private boolean mIsRunning = false;
    private Handler mUpdateHandler = new Handler();

    private Runnable mTimeTask = new Runnable() {
        @Override
        public void run() {
            mCountDownText.setText(String.valueOf(mCountTime));
            if (mCountTime > 1) {
                mCountTime--;
                mUpdateHandler.postDelayed(mTimeTask, 1000);
            } else {
                mCountTime = 10;
                mCamera.takePicture(null, null, DelayCameraActivity.this);
                mIsRunning = false;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delay_camera_page);
        initView();
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)//多个权限用","隔开
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean value) {
                        if (value){
                            if (mSurfaceCreated){
                                mSurfaceCreated = false;
                                initCamera();
                            }
                            Toast.makeText(getApplicationContext(), "已赋予权限", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "未赋予权限", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSurfaceCreated){
            mSurfaceCreated = false;
            initCamera();
        }
    }

    /**
     * 初始化界面
     * yumodev
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("DelayCamera");

        mCameraView = (SurfaceView) findViewById(R.id.surface);
        mCameraHolder = mCameraView.getHolder();
        //设置推送类型
        mCameraHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mCameraHolder.addCallback(this);

        mCameraView.setClickable(true);
        mCameraView.setFocusable(true);
        mCameraView.setFocusableInTouchMode(true);

        mCameraView.setOnClickListener(this);

        mCountDownText = (TextView) findViewById(R.id.count_down_text);
        Button beginBtn = (Button) findViewById(R.id.begin_btn);
        beginBtn.setOnClickListener(this);
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, TAG + "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, TAG + "onclick backimg");
                finish();
                break;
            }
            case R.id.surface:
            case R.id.begin_btn: {
                //mCamera.takePicture(null, null, this);
                if (!mIsRunning)
                    mUpdateHandler.post(mTimeTask);
                break;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "begin surfaceCreated");
        mSurfaceCreated = true;
        Log.d(TAG, "end surfaceCreated");
    }

    private void initCamera(){
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(mCameraHolder);
            Camera.Parameters parameters = mCamera.getParameters();

            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                parameters.setRotation(90);
                mCamera.setDisplayOrientation(90);
            }

            //设置颜色效果
            List<String> colorEffets = parameters.getSupportedColorEffects();
            Iterator<String> iter = colorEffets.iterator();
            while (iter.hasNext()) {

                String strColorEffet = iter.next();
                Log.d(TAG, "color Effets:" + strColorEffet);
                if (strColorEffet.equals(Camera.Parameters.EFFECT_SOLARIZE)) {
                    parameters.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
                }
            }
            mCamera.setParameters(parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d(TAG, "begin surfaceChanged");
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, " surfaceDestroyed");
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d(TAG, "begin onPictureTaken");
        Uri imageUri = this.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
        try {
            OutputStream imageStream = getContentResolver().openOutputStream(imageUri);
            imageStream.write(data);
            imageStream.flush();
            imageStream.close();
            Toast.makeText(this, "camera over", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCamera.startPreview();
    }
}
