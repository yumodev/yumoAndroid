/**
 * AlbumOneFragment.java
 * yumodev
 * 2014-12-25
 * 一个简单的本地相册浏览。非常简单的。嘿嘿。
 */
package com.yumo.android.test.media.album;

import java.io.File;
import java.io.IOException;

import com.yumo.android.R;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class AlbumOneFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "AlbumOneFragment";

    private Cursor mCursor = null;
    private int mFileColumn = -1;
    private int mTitleColumn = -1;
    private int mDisplayColumn = -1;

    private ImageView mImageView = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.album_one_page, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getAlbum();
    }

    /**
     * 初始化界面
     * yumodev
     * @return
     * boolean
     * 2014-12-3
     */
    private boolean initView() {
        Button beforeBtn = (Button) getView().findViewById(R.id.before_btn);
        beforeBtn.setOnClickListener(this);

        Button nextBtn = (Button) getView().findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this);

        mImageView = (ImageView) getView().findViewById(R.id.image);

        return true;
    }


    /**
     * 获取相册
     * yumodev
     * @return
     * boolean
     * 2014-12-25
     */
    private boolean getAlbum() {
        String[] columns = {Media.DATA, Media._ID, Media.TITLE, Media.DISPLAY_NAME};
        mCursor = getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
        mFileColumn = mCursor.getColumnIndexOrThrow(Media.DATA);
        mTitleColumn = mCursor.getColumnIndexOrThrow(Media.TITLE);
        mDisplayColumn = mCursor.getColumnIndexOrThrow(Media.DISPLAY_NAME);
        if (mCursor.moveToFirst()) {
            String filePath = mCursor.getString(mFileColumn);
            Bitmap bmp = getImage(filePath);
            if (bmp != null) {
                mImageView.setImageBitmap(bmp);
            }
        }
        return true;
    }

    private Bitmap getImage(String strFileName) {
        Log.d(TAG, " getImage fileName :" + strFileName);

        File file = new File(strFileName);
        if (!file.exists()) {
            Log.e(TAG, "getImage()  the fileName not exist :" + strFileName);
            return null;
        }
        //获取屏幕宽高
        Display currentDisplay = getActivity().getWindowManager().getDefaultDisplay();
        int widthDis = currentDisplay.getWidth();
        int heightDis = currentDisplay.getHeight();

        Log.d(TAG, "currentDisplay: width:" + widthDis + " height:" + heightDis);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(strFileName, bitmapOptions);


        Log.d(TAG, "first bmp width:" + bitmapOptions.outWidth + " height:" + bitmapOptions.outHeight /*+ " size:"+ bmp.getByteCount()*/);
        //计算尺寸比例
        int heightRadio = (int) Math.ceil(bitmapOptions.outHeight / (float) heightDis);
        int widthRadio = (int) Math.ceil(bitmapOptions.outWidth / (float) widthDis);

        Log.d(TAG, " radio widthRadio:" + widthRadio + "  heightradio:" + heightRadio);
        //设置缩放比例
        if (heightRadio > 1 || widthRadio > 1) {
            if (heightRadio >= widthRadio) bitmapOptions.inSampleSize = heightRadio;
            else if (heightRadio < widthRadio) bitmapOptions.inSampleSize = widthRadio;
        }

        //解码并显示图片。
        bitmapOptions.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(strFileName, bitmapOptions);

        Log.d(TAG, "after bap width:" + bmp.getWidth() + " height:" + bmp.getHeight() /*+ " size:"+bmp.getByteCount()*/);

        return bmp;
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

            /**拍摄时间*/
            String time = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            /**取最后修改时间作为文件创建时间*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, TAG + "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.before_btn: {
                if (mCursor.moveToPrevious()) {
                    String filePath = mCursor.getString(mFileColumn);
                    Bitmap bmp = getImage(filePath);
                    if (bmp != null) {
                        mImageView.setImageBitmap(bmp);
                    }
                } else {
                    if (mCursor.getCount() > 0) {
                        Toast.makeText(getActivity().getApplicationContext(), " this is the first picture", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), " no picture", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case R.id.next_btn: {
                if (mCursor.moveToNext()) {
                    String filePath = mCursor.getString(mFileColumn);
                    Bitmap bmp = getImage(filePath);
                    getPhotoInfo(filePath);
                    if (bmp != null) {
                        mImageView.setImageBitmap(bmp);
                    }
                } else {
                    if (mCursor.getCount() > 0) {
                        Toast.makeText(getContext().getApplicationContext(), " this is the lasted picture", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), " no picture", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }
}