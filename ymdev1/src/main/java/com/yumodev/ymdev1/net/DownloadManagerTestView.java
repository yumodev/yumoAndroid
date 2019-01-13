package com.yumodev.ymdev1.net;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;

/**
 * Created by yumo on 2018/6/27.
 */

public class DownloadManagerTestView extends YmTestFragment {

    private final String LOG_TAG = Log.LIB_TAG;
    private static final String DOWNLOAD_MANAGER_PACKAGE_NAME = "com.android.providers.downloads";

    private long mDownloadId = 0L;
    //private String mApkUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    private String mApkUrl = "https://pro-app-qn.fir.im/c8582d2daa700aac7cb0762c5833e8c6866e651c.apk?attname=app-release.apk_1.0.1.apk&e=1530102591&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:jUmGVC1RGtKBBqxN0zUOAmmZUDw=";
    //private String mApkUrl = "http://pic32.nipic.com/20130823/12976223_141018174311_2.jpg";
    private String mDirName = Environment.getDownloadCacheDirectory().getAbsolutePath();
    private String mFileName = "test.apk";

    final String URL1 = "http://www.appsapk.com/downloading/latest/Facebook-119.0.0.23.70.apk";


    public void testDownload(){
        mDownloadId = PRDownloader.download(URL1, getActivity().getFilesDir().getPath(), mFileName).build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        showToastMessage("onStartOrResume");
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        showToastMessage(progress.toString());
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        installAPK(getActivity(), mDownloadId);
                        showToastMessage("onDownloadComplete");
                    }

                    @Override
                    public void onError(Error error) {
                        showToastMessage(error.toString()+ error.isConnectionError()+" "+ error.isServerError());

                    }
                });
    }

    public void testCheckStatus(){
        Status status = PRDownloader.getStatus((int)mDownloadId);
        showToastMessage(status.toString());
    }

    public String getPath(){
        return YmAdFileUtil.getFileCachePath(getContext())+File.separator+"test.apk";
    }

    //下载到本地后执行安装
    private void installAPK(Context context, long downloadId) {
        //获取下载文件的Uri
        String filePath = mDirName+ File.separator+mFileName;
        Intent intent= new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
