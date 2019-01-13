package com.yumo.android.test.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.yumo.android.common.YumoConfig;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.media.YmImageUtil;
import com.yumo.common.net.YmFileNetUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yumodev on 17/9/21.
 *
 * http://wd.jb51.net:81/201502/books/HTML5yCSS3jcjc8(jb51.net).rar
 * http://pic.desk.chinaz.com/file/10.11.10/7/jillxs40.jpg
 */

public class NetTestView extends YmTestFragment {

    private final String LOG_TAG = "NetTestView";


    /**
     * 下载文件并保存到sd卡上。
     * yumodev
     * void
     * 2015-1-17
     */
    public void testDownImageDemo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imageUrl = "http://pic.desk.chinaz.com/file/10.11.10/7/jillxs40.jpg";
                String fileName = YmFileUtil.getFileNameFromPath(imageUrl);
                Bitmap bitmap = downImageWithHttpURLConnection(imageUrl);
                String message = "下载失败："+imageUrl;
                if (bitmap == null) {
                    showToastMessageOnUiThread(message);
                    Log.d(LOG_TAG, "downImageDemo error:" + imageUrl);
                    return;
                }


                String filePath = YumoConfig.getImageDirectory() + File.separator + fileName;
                try {
                    if (YmImageUtil.saveBitmapToFile(bitmap, filePath)) {
                        message = "downImageDemo success:" + filePath;
                    } else {
                        message = "downImageDemo error:" + filePath;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                showToastMessageOnUiThread(message);
                Log.i(LOG_TAG, message);
            }
        }).start();

    }

    /**
     * 直接从URL保存一张图片到Bitmap
     * @param
     * @return
     * String 返回图片路径，如果失败就返回为空
     * 2015-1-15
     */
    private Bitmap downImageWithHttpURLConnection(String urlName){
        HttpURLConnection httpCon = null;
        Bitmap bmp = null;
        try {
            URL url = new URL(urlName);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoInput(true);
            httpCon.connect();
            InputStream is = httpCon.getInputStream();

            bmp = BitmapFactory.decodeStream(is);
            httpCon.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }


    /**
     * 测试下载一个文件
     * yumodev
     * void
     * 2015-1-15
     */
    public void testDownFileDemo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fileUrl = "http://wd.jb51.net:81/201502/books/HTML5yCSS3jcjc8(jb51.net).rar";
                String fileName = YmFileUtil.getFileNameFromPath(fileUrl);
                String filePath = YumoConfig.getFileDirectory() + File.separator + fileName;
                boolean result = YmFileNetUtil.downFile(fileUrl, filePath);
                String message = "";
                if (result) {
                    message = "downFileDemo 下载文件成功";
                } else {
                    message = "downFileDemo 下载文件失败";
                }
                Log.i(LOG_TAG, message);
                showToastMessageOnUiThread(message);
            }
        }).start();
    }

    /**
     * 利用OkHttp打开网页
     */
    public void testGetHttpDataByOkhttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getByOkHttp("http://www.baidu.com");
            }
        }).start();

    }

    /**
     * yumodev 打印网站的数据 OkHttp
     * @param url 网站名称 比如http://www.baidu.com
     * @return int 返回请求结果
     * 2015-1-1
     */
    private int getByOkHttp(String url) {
        int statusCode = -1;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                Log.i(LOG_TAG, "getByAndroidHttp:" + response.body().toString());
                statusCode = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return statusCode;
    }

    /**
     * 利用OkHttp打开网页
     */
    public void testGetHttpDataByJava(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getByOkHttp("http://www.baidu.com");
            }
        }).start();

    }


    /**
     * yumodev 利用java的HttpConnectionURL 访问一个网站，并获取它的网站数据。
     * @param webSite 要下载web页面的网址，比如Http://www.baidu.com
     * @return int
     * 2015-1-1
     */
    private int getByJavaHttp(String webSite) {
        int statusCode = -1;
        try {
            //见一个一个URL
            URL url = new URL(webSite);
            //访问web页面
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            //获取返回的状态吗。
            statusCode = httpConnection.getResponseCode();
            //获取下砸的web页面数据。
            InputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            Reader reader = new InputStreamReader(inputStream);
            String strResult = "";
            int c;
            while ((c = reader.read()) != -1) {
                strResult += String.valueOf((char) c);
            }
            Log.d(LOG_TAG, "getByJavaHttp: " + strResult);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode;
    }
}
