/**
 * AudioService.java
 * yumo
 * 2015-1-25
 */
package com.yumo.android.test.service;

import java.io.File;
import java.io.IOException;

import com.yumo.android.R;
import com.yumo.android.common.inface.ProgressInterface;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

/**
 * yumodev 音乐播放
 */
public class AudioService extends Service {

    private static final String TAG = "ymplayer";

    private MediaPlayer mMediaPlayer = null;

    private final AudioServiceBinder mBinder = new AudioServiceBinder();

    private ProgressInterface mProgressListener = null;

    private String mHttpUrl = "http://sc1.111ttt.cn:8282/2018/1/03/13/396131229550.mp3";

    public class AudioServiceBinder extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }

        public void startPlayHttp(){
            startPlay();
        }

        public boolean isPlayering(){
            return isPlaying();
        }

        public void onProgressListener(ProgressInterface listener){
            setProgressListener(listener);
        }

        public void forward(int time){
            forwardPlayer(time);
        }

        public void back(int time){
            backPlayer(time);
        }

        public void pause(){
            pausePlayer();
        }

        public int getPlayingPosition(){
            return getCurrentPosition();
        }

        public void seekTo(int progress){
            seekToProgess(progress);
        }
    }

    public AudioService() {
    }

    /* (non-Javadoc)
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            if (mProgressListener != null) mProgressListener.setMax(mMediaPlayer.getDuration());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public boolean playLocalMusic() {
        if (mMediaPlayer == null) {
            File dir = Environment.getExternalStorageDirectory();
            String fileName = dir + File.separator + "HJNCE/res/NCE1/NCE1012.mp3";
            Log.d(TAG, TAG + ":" + fileName);
            File file = new File(fileName);
            mMediaPlayer = MediaPlayer.create(this, Uri.fromFile(file));
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    Log.d(TAG, "completion");
                    mp.start();
                }
            });
            mMediaPlayer.start();
            if (mProgressListener != null) mProgressListener.setMax(mMediaPlayer.getDuration());
        }

        return true;
    }

    private void startPlay(){
        if (mMediaPlayer != null){
            mMediaPlayer.start();
        }else{
            playMusicRaw();
        }
    }

    private boolean playMusicRaw(){
        destroyMedia();
        mMediaPlayer = MediaPlayer.create(this, R.raw.test);
        mMediaPlayer.setOnCompletionListener(mp -> {
            mp.start();
            Log.i(TAG, "completion");
        });

        mMediaPlayer.setOnSeekCompleteListener(mp -> {
            Log.i(TAG, "seek complete");
        });
        mMediaPlayer.start();
        return true;
    }

    /**
     * 在线播放mp3文件
     * @return boolean
     * 2015-1-26
     */
    private boolean playMusicHttp() {
        destroyMedia();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mHttpUrl);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.prepareAsync();
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mp -> {
           // Log.i(TAG, "completion");
            mp.start();
        });

        //发生错误的情况
        mMediaPlayer.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onError:" + what);
                return false;
            }
        });

        //准备播放
        mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "audio onPrepared:");
                Log.d(TAG, "playMusicHttp: getDuration:" + mMediaPlayer.getDuration());
                if (mProgressListener != null) mProgressListener.setMax(mMediaPlayer.getDuration());
            }
        });

        //缓冲进度
        mMediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.d(TAG, "audio onBufferingUpdate:" + percent + " duration:" + mp.getDuration());
                int secondProgress = mp.getDuration() * percent / 100;
                mProgressListener.setMax(secondProgress);
            }
        });

        return true;
    }

    private void pausePlayer(){
        Log.i(TAG, "pause");
        if (mMediaPlayer == null){
            return;
        }

        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
    }

    /**
     * 前进多少秒
     * yumo
     * @param second
     * @return boolean
     * 2015-1-26
     */
    private boolean forwardPlayer(int second) {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.getDuration() > 0)
                mMediaPlayer.seekTo(mMediaPlayer.getDuration() + second * 1000);
        }

        return true;
    }

    /**
     * 重新播放
     * @return
     */
    private boolean onReStart() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
        return true;
    }

    private boolean seekToProgess(int millSec) {
        if (mMediaPlayer != null && millSec >= 0) {
            mMediaPlayer.seekTo(millSec);
        }

        if (!mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
        return true;
    }

    /**
     * 获取当前位置
     * @return
     */
    private int getCurrentPosition() {
        if (mMediaPlayer == null){
            return 0;
        }
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 获取时长
     * @return
     */
    private int getDuration() {
        if (mMediaPlayer == null){
            return 0;
        }
        return mMediaPlayer.getDuration();
    }

    /**
     * 获取播放器
     * @return
     */
    private MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    /**
     * 是否在播放
     * @return
     */
    private boolean isPlaying() {
        if (mMediaPlayer == null){
            return false;
        }
        return mMediaPlayer.isPlaying();
    }

    /**
     * 后退多少秒。
     * yumo
     *
     * @param second
     * @return boolean
     * 2015-1-26
     */
    private boolean backPlayer(int second) {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.getDuration() > 0)
                mMediaPlayer.seekTo(mMediaPlayer.getDuration() - second * 1000);
        }

        return true;
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.i(TAG, "bindService");
        return super.bindService(service, conn, flags);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.i(TAG, "unbindService");
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        super.onDestroy();
    }

    private void setProgressListener(ProgressInterface listener) {
        this.mProgressListener = listener;
    }

    /**
     * 释放音乐播放器
     */
    private void destroyMedia(){
        if (mMediaPlayer == null){
            return;
        }

        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }

        mMediaPlayer.setOnCompletionListener(null);
        mMediaPlayer.setOnPreparedListener(null);
        mMediaPlayer.setOnErrorListener(null);
        mMediaPlayer.setOnInfoListener(null);
        mMediaPlayer.setOnBufferingUpdateListener(null);
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}