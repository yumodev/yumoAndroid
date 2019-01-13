package com.yumo.android.test.media.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.util.Log;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.util.YmSecureUtil;
import com.yumo.common.media.YmImageUtil;
import com.yumo.common.net.YmFileNetUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by image on 5/8/16.
 * 图片相关的测试类
 */
public class TestImageUtilsView extends YmTestFragment {
    private final String LOG_TAG = "TestFragment_ImageUtils";
    //private String mImageUrl = "http://pic.desk.chinaz.com/file/10.11.10/7/jillxs40.jpg";
    private String mImageUrl = "http://www.baidu.com/img/2016_5_8logo_9b520fd369fb6fe292bec8b820615fc3.gif";

    private String getImageDir() {
        return YmAdFileUtil.getFileCache(getContext(), "iamge");
    }

    public void testDownImageToBitmap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bmp = YmImageUtil.saveImageToBitmap(mImageUrl);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bmp == null) {
                            showToastMessage("download failed");
                        } else {
                            showToastMessage("download success");
                            Log.d(LOG_TAG, "width:" + bmp.getWidth() + "height:" + bmp.getHeight());
                        }
                    }
                });
            }
        }).start();
    }

    public void testDownImageToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fileName = getImageDir() + File.separator + UUID.randomUUID().toString();
                final boolean result = YmFileNetUtil.downFile(mImageUrl, fileName);

                try {
                    String hash = YmSecureUtil.getFileMD5(fileName);
                    BitmapFactory.Options options = YmImageUtil.getOptionsFromFile(fileName);
                    Log.d(LOG_TAG, "hash:" + hash + "width:" + options.outWidth + " height:" + options.outHeight + " MiniType:" + options.outMimeType);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!result) {
                            showToastMessage("download failed");
                        } else {
                            showToastMessage("download success");
                        }
                    }
                });
            }
        }).start();
    }


    public void testImageGps(){
        ///storage/emulated/0/camera_demo_two.jpg
        //getPhotoInfo("storage/emulated/0/camera_demo_two.jpg");
        getPhotoInfo("storage/emulated/0/DCIM/Camera/IMG_20181018_093427.jpg");

    }

    public  void getPhotoInfo(String filePath){
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            String width = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            String length = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            /**纬度*/
            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            /**经度*/
            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            float lat = convertRationalLatLonToFloat(latitude, latRef);
            float lng = convertRationalLatLonToFloat(longitude, lngRef);

            /**拍摄时间*/
            String time = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            /**取最后修改时间作为文件创建时间*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static float convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return (float) -result;
        }

        BigDecimal bigDecimal = new BigDecimal(result);
        bigDecimal = bigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }


}
