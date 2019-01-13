/**
 * AudioDemoActivity.java
 * yumo
 * 2014-12-4
 */
package com.yumo.android.test.media;

import java.io.File;

import com.yumo.android.R;
import com.yumo.android.common.inface.ProgressInterface;
import com.yumo.android.test.service.AudioService;
import com.yumo.common.android.YmToastUtil;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * yumodev
 */
public class AudioDemoActivity extends Activity implements View.OnClickListener {
    private final String TAG = "ymplayer";

    private MediaPlayer mMediaPlayer = null;

    private Intent mIntentService = null;

    private SeekBar mSeekBar = null;

    //private AudioService mBinderService = null;
    private AudioService.AudioServiceBinder mPlayBinder = null;

    private Boolean mPlayStop = false;

    private Thread mPlayThread = null;

    private Button mPlayAudioServiceBtn = null;

    private Button mForwardAudioServiceBtn = null;

    private Button mBackAudioServiceBtn = null;

    private ServiceConnection mServiceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            //mBinderService = ((AudioService.AudioServiceBinder) service).getService();
            mPlayBinder = (AudioService.AudioServiceBinder)service;
            mPlayBinder.onProgressListener(mProgerssListener);
            //mBinderService.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPlayBinder = null;
            Log.i(TAG, "onServiceDisConnected");
        }
    };

    private ProgressInterface mProgerssListener = new ProgressInterface() {
        @Override
        public void setMax(int max) {
            Log.i(TAG, "progressListener setMax : " + max);
            if (mSeekBar != null) mSeekBar.setMax(max);
        }

        @Override
        public void setProgress(int progress) {
            Log.i(TAG, "progressListener setProgress : " + progress);
            if (mSeekBar != null) mSeekBar.setProgress(progress);
        }

        @Override
        public void setSecondProgress(int progress) {
            Log.i(TAG, "progressListener setSecondProgress : " + progress);
            if (mSeekBar != null) mSeekBar.setSecondaryProgress(progress);
        }

    };


    class playRunable implements Runnable {
        @Override
        public void run() {
            while (!mPlayStop) {
                Log.d(TAG, "playRunable:");
                if (mSeekBar != null && mPlayBinder != null) {
                    mSeekBar.setProgress(mPlayBinder.getPlayingPosition());
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.audio_demo_page);
        initView();
    }

    /**
     * yumo
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("Audio");

        Button btnAudioSystem = (Button) findViewById(R.id.btn_audio_system);
        btnAudioSystem.setOnClickListener(this);

        //自定义播放音频
        Button beginAudio = (Button) findViewById(R.id.begin_audio);
        Button endAudio = (Button) findViewById(R.id.end_audio);

        beginAudio.setOnClickListener(this);
        endAudio.setOnClickListener(this);

        Button beginAudioService = (Button) findViewById(R.id.begin_audio_service);
        Button endAudioService = (Button) findViewById(R.id.end_audio_service);
        mBackAudioServiceBtn = (Button) findViewById(R.id.back_audio_service);
        mForwardAudioServiceBtn = (Button) findViewById(R.id.forward_audio_service);
        mPlayAudioServiceBtn = (Button) findViewById(R.id.pause_audio_service);

        beginAudioService.setOnClickListener(this);
        endAudioService.setOnClickListener(this);
        mBackAudioServiceBtn.setOnClickListener(this);
        mForwardAudioServiceBtn.setOnClickListener(this);
        mPlayAudioServiceBtn.setOnClickListener(this);

        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onStopTrackingTouch:" + seekBar.getProgress());
                mPlayBinder.seekTo(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onStartTrackingTouch:" + seekBar.getProgress());
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onProgress:" + seekBar.getProgress());
            }
        });

        return true;
    }

    @Override
    protected void onStart() {
        Log.d(TAG,  "onStart");
        super.onStart();
//        File scard = Environment.getExternalStorageDirectory();
//        String fileName = scard + File.separator + "HJNCE/res/NCE1/NCE1012.mp3";
//        Log.d(TAG,  ":" + fileName);
//        File file = new File(fileName);
//        mMediaPlayer = MediaPlayer.create(this, Uri.fromFile(file));
//        mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
//
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                // TODO Auto-generated method stub
//                Log.d(TAG, "completion");
//                mp.start();
//            }
//        });

        startService();

    }

    @Override
    protected void onRestart() {
        Log.d(TAG,  "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG,  "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG,  "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG,  "onStop");
        super.onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }

        stopService();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,  "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG,  "onclick backimg");
                finish();
                break;
            }
            case R.id.btn_audio_system: {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                File scard = Environment.getExternalStorageDirectory();
                String fileName = scard + File.separator + "HJNCE/res/NCE1/NCE1012.mp3";
                Log.d(TAG,  ":" + fileName);
                File file = new File(fileName);
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                startActivity(intent);
                break;
            }
            case R.id.begin_audio: {
                //if(mMediaPlayer != null) mMediaPlayer.start();
                //startMediaPlayByTheadTest();
                Log.d(TAG, "begin audio");
                break;
            }
            case R.id.end_audio: {
                if (mMediaPlayer != null) mMediaPlayer.pause();
                Log.d(TAG, "end audio");
                break;
            }
            case R.id.begin_audio_service: {
                Log.d(TAG, "begin_audio_service");
                startService();
                break;
            }
            case R.id.end_audio_service: {
                stopService();
                break;
            }
            case R.id.forward_audio_service: {
                //前进5秒
                if (mPlayBinder != null) mPlayBinder.forward(5);
                break;
            }
            case R.id.back_audio_service: {
                //后退5秒
                if (mPlayBinder != null) mPlayBinder.back(5);
                break;
            }
            case R.id.pause_audio_service: {
                //重新启动
                if (mPlayBinder == null) {
                    startService();
                    YmToastUtil.showMessage("启动服务");
                } else {
                    if (mPlayBinder.isPlayering()) {
                        mPlayBinder.pause();
                        mForwardAudioServiceBtn.setEnabled(false);
                        mBackAudioServiceBtn.setEnabled(false);
                        mPlayAudioServiceBtn.setText("播放");
                    } else {
                        mPlayBinder.startPlayHttp();
                        mForwardAudioServiceBtn.setEnabled(true);
                        mBackAudioServiceBtn.setEnabled(true);
                        mPlayAudioServiceBtn.setText("暂停");
                        //startPlayMusicThread();
                    }
                }

                break;
            }
        }
    }


    /**
     * 测试在线程里面开启一个音乐
     * yumodev
     * void
     * 2015-1-25
     */
    private void startMediaPlayByTheadTest() {
        new Thread() {
            public void run() {
                if (mMediaPlayer != null) mMediaPlayer.start();
            }
        }.start();

    }


    public void startPlayMusicThread() {
        if (mPlayThread != null) {
            mPlayThread.interrupt();
            mPlayThread = null;
        }

        mPlayThread = new Thread(new playRunable());
        mPlayThread.start();
    }

    /**
     * 开启服务
     * yumo
     * void
     * 2015-1-26
     */
    public void startService() {
        if (mIntentService == null) {
            mIntentService = new Intent(this, AudioService.class);
        }
        startService(mIntentService);
        bindService(mIntentService, mServiceCon, Context.BIND_AUTO_CREATE);
    }

    /**
     * 关闭服务
     * yumo
     * void
     * 2015-1-26
     */
    public void stopService() {
        if (mServiceCon != null) {
            unbindService(mServiceCon);
            stopService(mIntentService);
        }
        mPlayStop = true;
    }
}
