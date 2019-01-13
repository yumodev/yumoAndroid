package com.yumodev.ymdev1.net;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;


import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;

/**
 * Created by yumo on 2018/6/27.
 */

public class DownloadManage2rTestView extends YmTestFragment {

    private final String LOG_TAG = Log.LIB_TAG;
    private static final String DOWNLOAD_MANAGER_PACKAGE_NAME = "com.android.providers.downloads";

    private int mDownloadId = 0;
    //private String mApkUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    private String mApkUrl = "https://pro-app-qn.fir.im/c8582d2daa700aac7cb0762c5833e8c6866e651c.apk?attname=app-release.apk_1.0.1.apk&e=1530102591&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:jUmGVC1RGtKBBqxN0zUOAmmZUDw=";
    //private String mApkUrl = "http://pic32.nipic.com/20130823/12976223_141018174311_2.jpg";
    private String mDirName = "";
    private String mFileName = "test.apk";

    final String URL1 = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";


    public void testDownload(){
        mDownloadId = FileDownloader.getImpl().create(mApkUrl).setWifiRequired(true).setPath(getPath()).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                showToastMessage(task.getUrl());

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                int percent=(int) ((double) soFarBytes / (double) totalBytes * 100);
                showToastMessage(percent+"");
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                showToastMessage("blockComplete");

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                showToastMessage("下载完成");
                installAPK(getContext(), mDownloadId);
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                showToastMessage("paused");
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                showToastMessage(e.getMessage()+ " " +task.getPath()+" "+task.getFilename());
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                showToastMessage(task.getFilename()+" waring");
            }
        }).start();
    }

    private String getPath(){
        return getContext().getExternalCacheDir().getPath() + File.separator+ mFileName;
    }

    public void testCheckStatus(){
        long total = FileDownloader.getImpl().getTotal(mDownloadId);
        long sofar =  FileDownloader.getImpl().getSoFar(mDownloadId);
        int status = FileDownloader.getImpl().getStatus(mDownloadId, getPath());
        int status1 =  FileDownloader.getImpl().getStatus(URL1, getPath());
        int status2 = FileDownloader.getImpl().getStatusIgnoreCompleted(mDownloadId);

        showToastMessage(total+" "+sofar + " "+ status+" "+status1+" "+status2);
    }

    public void testDeleteDownloadId(){
        FileDownloader.getImpl().clear(mDownloadId, getPath());
    }
    public void testInstallAPk(){
        installAPK(getContext(), mDownloadId);
    }

    //下载到本地后执行安装
    private void installAPK(final Context context, long downloadId) {
        new Handler(getContext().getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //获取下载文件的Uri
                String filePath = getPath();
                Intent intent= new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
