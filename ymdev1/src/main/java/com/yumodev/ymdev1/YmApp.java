package com.yumodev.ymdev1;

import android.app.Application;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.liulishuo.filedownloader.FileDownloader;
import com.tencent.smtt.sdk.QbSdk;
import com.ymdev.download.providers.DownloadLib;
import com.yumo.common.android.YmContext;
import com.yumo.common.log.Log;

/**
 * Created by yumodev on 17/9/11.
 */

public class YmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YmContext.setAppContext(this);
        initX5();
        initDownload();
        FileDownloader.setup(this);//注意作者已经不建议使用init方法
       // DownloadManager.init(this);
        DownloadLib.init(this);
    }

    private void initX5(){
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb;
        cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.i("X5WebView", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.i("X5WebView", " onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initDownload(){
        PRDownloader.initialize(getApplicationContext());

// Enabling database for resume support even after the application is killed:
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);
    }
}
