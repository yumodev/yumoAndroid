package com.yumo.android.test.net.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.yumo.android.common.YumoConfig;
import com.yumo.android.common.utils.DownImageUtils;
import com.yumo.common.media.YmImageUtil;
import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wks on 3/2/16.
 * http 测试
 */
public class OkHttpTestView extends YmTestFragment {

    private final String LOG_TAG = "OkHttpTestView";

    public void testGetBaiduPage(){
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                gethttpage("www.baidu.com");
            }
        });
    }

    /**
     * 同步获取一个网页的内容
     * @param url
     */
    private void gethttpage(final String url){
        //配置缓存的路径，和缓存空间的大小
        Cache cache = new Cache(new File("/sdcard/yumo"),10*10*1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                //打开缓存
                .cache(cache)
                .build();

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()){
                Log.d(LOG_TAG, "getByAndroidHttp:" + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testGetPage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                for (int n =0; n < 10; n++){
                    String url = "http://sina.com.cn";

                    Request request = new Request.Builder().url(url).build();

                    try {
                        Response response = client.newCall(request).execute();
                        if (response != null && response.isSuccessful()){
                            Log.d(LOG_TAG, "getByAndroidHttp:" + response.body().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * 测试下载文件
     */
    public void testDownFile(){
        String url = "http://xz6.jb51.net:81/201601/books/Java_JVM(jb51.net).rar";
        new DownFileUrlAsyncTask().execute(url);
        for (int n=0; n<5; n++){
            DownFileUrlAsyncTask task = new DownFileUrlAsyncTask();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        }

    }

    class DownFileUrlAsyncTask extends AsyncTask<String, Integer, Long>{

        @Override
        protected Long doInBackground(String... params) {
            String url = params[0];
            int num = (new Random(100)).nextInt();
            String name = YumoConfig.getFileDirectory()+File.separator+ num+"_Java_JVM(jb51.net).rar";
            return downFile(url,name);

        }
        @Override
        protected void onPostExecute(Long fileSize) {
            super.onPostExecute(fileSize);
            showToastMessage("get "+ fileSize + " data success");
        }
    }

    /**
     * 下载一个文件，注意使用的时候需要放到一个线程里面。
     * yumo
     *
     * @param url      文件源路径
     * @param fileName 文件下载后要保存的路径
     * @return int 大于0 表示返回的文件的大小；－1:参数不对；－3:表示网络不对；－2:文件路径不对
     * 2015-1-15
     */
    public long downFile(String url, String fileName) {
        //传入参数失败
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(fileName)) {
            return 0;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                FileOutputStream fileStream = new FileOutputStream(fileName);
                InputStream is = response.body().byteStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                int len = 0;
                byte[] buffer = new byte[1024];

                while ((len = bis.read(buffer)) != -1) {
                    fileStream.write(buffer, 0, len);
                }

                fileStream.close();
                bis.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (new File(fileName)).length();
    }


    /**
     * 测试获取ip
     */
    public void testGetIp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = YmOkHttpUtil.getBodyString("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=59.108.54.37");
                    Log.i(LOG_TAG, "testGetIp"+result);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(LOG_TAG, "testGetIp"+e.getMessage());

                }
            }
        }).start();
    }

    /**
     * 获取淘宝IP
     */
    public void testTaoIp(){
        RequestBody body = new FormBody.Builder()
                .add("ip", "59.108.54.37")
                .build();

        Request request = new Request.Builder()
                .url("http://ip.taobao.com/service/getIpInfo.php?ip=59.108.54.37")
                //.post(body)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(LOG_TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(LOG_TAG, "onResponse:"+response.body().string());
            }
        });
    }


    public void testDownloadFile(){
       // final String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        final String url = "http://www.zebrai.cn/login/picture/getCaptcha";
        new Thread(new Runnable() {
            @Override
            public void run() {
               downloadFile(url);
            }
        }).start();

    }

    public void testDownloadAndShowImage(){
        //final String url = "http://www.zebrai.cn/login/picture/getCaptcha";
        final String url = "https://ict.image.alimmdn.com/banma_h5/zebrai/zebra_login_bg.png";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String hash = DownImageUtils.downloadImage(url);
                final String fileName = DownImageUtils.getImagePath(hash);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //YmImageUtil.showImageInView(getContext(), fileName);
                        showImage(fileName);
                    }
                });
            }
        }).start();
    }
    private void showImage(String fileName){
        ImageView imageView = new ImageView(getContext());

        BitmapFactory.Options options = YmImageUtil.getOptionsFromFile(fileName);
        Log.d(LOG_TAG,  "width:" + options.outWidth + " height:" + options.outHeight + " MiniType:" + options.outMimeType);

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);

        imageView.setImageBitmap(bitmap);
        showTestView(imageView);
    }

    private void downloadFile(String url){
        String fileName = Uri.parse(url).getLastPathSegment();

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(builder.build()).execute();
            //long length = Long.parseLong(response.header("Content-Length"));
            long hasDown = 0;
            InputStream inputStream = response.body().byteStream();
            byte[] data = new byte[4096];
            for (;;){
                int len = inputStream.read(data);
                if (len == -1){
                    break;
                }
                hasDown += len;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class GetUrlAsyncTask extends AsyncTask<String, Integer , String>{

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();
                if (response != null && response.isSuccessful()){
                    Log.d(LOG_TAG, "getByAndroidHttp:" + response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return url;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showToastMessage("get "+ s+ " data success");
        }
    }
}
