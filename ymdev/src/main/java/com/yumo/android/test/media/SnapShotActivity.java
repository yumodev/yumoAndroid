/**
 * SnapShotActivity.java
 * yumo
 * 2014-12-25
 */
package com.yumo.android.test.media;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import com.yumo.android.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * yumodev
 */
public class SnapShotActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback, Camera.PictureCallback {
    private final String TAG = SnapShotActivity.class.getSimpleName() + "  ";

    private SurfaceView mCameraView = null;

    private SurfaceHolder mCameraHolder = null;

    private Camera mCamera = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.snapshot_page);
        initView();
    }

    /**
     * 初始化界面
     * yumodev
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("SnapShot");

        mCameraView = (SurfaceView) findViewById(R.id.surface);
        mCameraHolder = mCameraView.getHolder();
        //设置推送类型
        mCameraHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mCameraHolder.addCallback(this);

        mCameraView.setClickable(true);
        mCameraView.setFocusable(true);
        mCameraView.setFocusableInTouchMode(true);

        mCameraView.setOnClickListener(this);
        return true;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, TAG + "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, TAG + "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, TAG + "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, TAG + "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Log.d(TAG, TAG + "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, TAG + "onclick backimg");
                finish();
                break;
            }
            case R.id.surface: {
                mCamera.takePicture(null, null, this);
                break;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d(TAG, "begin surfaceCreated");
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(holder);
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
        Log.d(TAG, "end surfaceCreated");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        Log.d(TAG, "begin surfaceChanged");
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d(TAG, " surfaceDestroyed");
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d(TAG, "begin onPictureTaken");
        // TODO Auto-generated method stub
        Uri imageUri = this.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
        try {
            OutputStream imageStream = getContentResolver().openOutputStream(imageUri);
            imageStream.write(data);
            imageStream.flush();
            imageStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCamera.startPreview();
    }
}

