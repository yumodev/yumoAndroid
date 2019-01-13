package com.yumodev.process.media;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yumo.common.io.YmAssertUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.Define;
import com.yumodev.process.R;

import java.io.IOException;

/**
 * Created by yumo on 2018/4/24.
 */

public class TestPlayer extends YmTestFragment{
    private MediaPlayer mMediaPlayer = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediaPlayer = new MediaPlayer();
    }

    public void testA(){

    }

    public void testPlayer(){
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.dig_game_over);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        StringBuilder  sb = new StringBuilder();
        sb.append(mediaPlayer.getDuration()+" ");
        showToastMessage(sb.toString());
    }

    public void testStartDigGameOver(){
        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.dig_game_over);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }


    public void testStartDigSuprise(){
        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.dig_suprise);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public void testStartDiaBackground(){
        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.dig_background_music);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public void testStartSilent(){
        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.silent);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public void testStartPlay(){
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void testStopPlay(){
        mMediaPlayer.stop();
    }

    public void testReadMp3(){
        String silent="";
        try {
            silent = YmAssertUtil.getAssertFileToString(getContext(), "silent.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(Define.mDebugLog, silent);
    }

    public void testReadMp3Bytes(){
        byte[] silent = null;
        try {
            silent = YmAssertUtil.getAssertFileToBytes(getContext(), "silent.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int num = 5;
        int length = silent.length / num + 1;
        //Log.i(Define.mDebugLog, new String(silent));

        for (int i = 0; i < num; i++){
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            byte[] bytes = new byte[length];
            int n = 0;
            for (int j = length * i; j < (i+1)*length && j < silent.length; j++){
                sb.append(silent[j]+",");
                bytes[n++] = silent[j];
            }
            sb.append("}");

            Log.i(Define.mDebugLog, sb.toString());

            YmFileUtil.saveFile("/sdcard/ymprocess/mp3"+i+".txt", sb.toString());
            YmFileUtil.saveFile("/sdcard/ymprocess/silent1.mp3", bytes, true);

        }
    }

    public void testSaveReadMp3File(){
        byte[] silent = null;
        try {
            silent = YmAssertUtil.getAssertFileToBytes(getContext(), "silent.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        YmFileUtil.saveFile("/sdcard/ymprocess/silent.mp3", silent);
    }

    public void testSaveMap3(){
       // YmFileUtil.saveFile("/sdcard/ymprocess/slient.mp3", mp3);
    }

    public void testPlayerMp3FromFile(){
        MediaPlayer mp = new MediaPlayer();
        //Uri setDataSourceuri = Uri.parse("android.resource://com.android.sim/"+R.raw.test);
        Uri setDataSourceuri = Uri.parse("/sdcard/ymprocess/silent1.mp3");
        try {
            mp.setDataSource(getContext(), setDataSourceuri);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testMergeFile(){
        YmFileUtil.saveFile("/sdcard/ymprocess/silent1.mp3", MediaOne.getBytes(), true);
        YmFileUtil.saveFile("/sdcard/ymprocess/silent1.mp3", MediaTwo.getBytes(), true);
        YmFileUtil.saveFile("/sdcard/ymprocess/silent1.mp3", MediaThree.getBytes(), true);
        YmFileUtil.saveFile("/sdcard/ymprocess/silent1.mp3", MediaFour.getBytes(), true);
        YmFileUtil.saveFile("/sdcard/ymprocess/silent1.mp3", MediaFive.getBytes(), true);

    }
}
