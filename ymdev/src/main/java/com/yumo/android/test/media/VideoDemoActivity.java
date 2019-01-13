/**
 * VideoDemoActivity.java
 * yumo
 * 2014-12-4
 * TODO
 */
package com.yumo.android.test.media;

import java.io.File;

import com.yumo.android.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * yumo
 */
public class VideoDemoActivity extends Activity implements View.OnClickListener {
    private final String TAG = VideoDemoActivity.class.getSimpleName() + "  ";
    private VideoView mVideoView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.video_demo_page);
        initView();
    }

    /**
     * TODO 初始化界面
     * yumo
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("Video");

        mVideoView = (VideoView) findViewById(R.id.videoView);
        playVideoHttp();
        return true;
    }

    private void playVideo() {
        File file = new File("/storage/sdcard0/DCIM/Camera/VID_20131208_161421.mp4");
        mVideoView.setVideoURI(Uri.fromFile(file));
        mVideoView.start();
    }

    private void playVideoHttp() {
        Uri uri = Uri.parse("http://101.26.37.79/v.cctv.com/flash/mp4video6/TMS/2010/12/05/641b5afeb00e4da6f29cb2a2b8204768_h264418000nero_aac32-7.mp4?start=158&wshc_tag=0&wsts_tag=54c79b3a&wsid_tag=dddd993f&wsiphost=ipdbm");
        mVideoView.setVideoURI(uri);
        mVideoView.start();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, TAG + "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, TAG + "onRestart");
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
        if (mVideoView != null) mVideoView.pause();

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
        }
    }


}
