package com.yumodev.process.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.yumo.common.util.YmDateUtil;
import com.yumodev.process.R;
import com.yumodev.process.background.OnePxActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yumo on 2018/4/20.
 */

/**
 * Created by yumo on 2018/4/20.
 * 播放音乐
 */

public class MediaClientService {
    private final String LOG_TAG = "MediaClientService";
    private final long MEDIA_PLAY_TIME_IN_BACKGROUND = 1000 * 60 * 5;
    private MediaPlayer mMediaPlayer = null;
    private Context mContext = null;
    private static Object mediaLock = new Object();
    private Timer mTimer = null;
    /**
     * 是否在定时播放
     */
    private boolean mTimPlaying = false;
    private ScreenHelper mScreenHelper = null;
    private MediaClientService(){}

    public static MediaClientService getInstance(){
        return SingletonHolder.mInstance;
    }

    public void init(Context context){
        mContext = context;
        mMediaPlayer = MediaPlayer.create(context, R.raw.silent);
        mMediaPlayer.setLooping(true);
        mScreenHelper = new ScreenHelper(context);

        mScreenHelper.requestScreenStateUpdate(new ScreenHelper.ScreenStateListener() {
            @Override
            public void onScreenOn() {
              // cancelTimingPlayer();


                mContext.sendBroadcast(new Intent("finish activity"));
            }

            @Override
            public void onScreenOff() {
               // timingPlayer(MEDIA_PLAY_TIME_IN_BACKGROUND);
                Intent it = new Intent(mContext, OnePxActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(it);
            }

            @Override
            public void onUserPresent() {

            }
        });

    }

    private void initPlayer(){
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(mContext, R.raw.silent);
            mMediaPlayer.setLooping(true);
        }
    }
    /**
     * 开启行车，开始播放音乐
     */
    public void startDrvingPlayer() {
        initPlayer();

        cancelTimingPlayer();
        Log.d(LOG_TAG, "startPlayer");
        if (isPlayer()){
            return;
        }

        mMediaPlayer.start();
    }



    /**
     * 结束播放
     */
    public void stopPlayer() {
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        Log.d(LOG_TAG, "stopPlayer");
    }

    public void timingPlayer(final long delay){
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                stopPlayer();
                Log.d(LOG_TAG, Thread.currentThread().getName());
                Vibrator vibrator = (Vibrator)mContext.getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                timer.cancel();
            }
        };

        timer.schedule(task, delay);
    }

    /**
     * 锁屏或者退入后台后，锁屏播放音乐
     * @param time
     */
    public void timingPlayer1(final long time){
        if (mMediaPlayer == null && mTimPlaying){
            return;
        }

        Log.d(LOG_TAG, "begin timingPlayer");
        //定时播放音乐后停止，如果期间开始行车，就退出，不在即时播放。
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mediaLock){
                    if (!isPlayer()){
                        initPlayer();
                        try {
                            mMediaPlayer.prepare();
                            mMediaPlayer.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    mTimPlaying = true;
                    try {
                        //mediaLock.wait(time);
                        Thread.sleep(time);
                        Log.d(LOG_TAG, "wait finished");
                        stopPlayer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d(LOG_TAG, e.getMessage());
                        stopPlayer();
                    }finally {
                        //stopPlayer();
                        Log.d(LOG_TAG, "timing play finished");
                        mTimPlaying = false;
                    }
                }
            }
        }).start();
    }

    public void cancelTimingPlayer(){
        if (mTimPlaying){
            try {
                synchronized (mediaLock){
                    mediaLock.notify();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否正在播放音乐
     * @return
     */
    public boolean isPlayer(){
        if (mMediaPlayer != null){
            try {
                return mMediaPlayer.isPlaying();
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }

        return false;
    }

    private static class SingletonHolder {
        private final static MediaClientService mInstance = new MediaClientService();
    }

}
