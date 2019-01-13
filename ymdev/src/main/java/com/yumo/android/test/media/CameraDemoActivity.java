/**
 * CameraDemoActivity.java
 * yumo
 * 2014-12-4
 * TODO
 */
package com.yumo.android.test.media;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.yumo.android.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * yumodev
 */
public class CameraDemoActivity extends Activity implements View.OnClickListener {
    private final String TAG = CameraDemoActivity.class.getSimpleName() + "  ";
    private final int REQUEST_CODE_CAMEAR_ONE = 1;
    private final int REQUEST_CODE_CAMEAR_TWO = 2;
    private final int REQUEST_CODE_CAMEAR_THREE = 3;
    private String mImageFilePath = "";

    private Uri mImageUri = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.camera_demo_page);
        initView();
    }

    /**
     * yumodev
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("Camera");

        Button btnOne = (Button) findViewById(R.id.btn_one);
        btnOne.setOnClickListener(this);

        Button btnTwo = (Button) findViewById(R.id.btn_two);
        btnTwo.setOnClickListener(this);

        Button btnThree = (Button) findViewById(R.id.btn_three);
        btnThree.setOnClickListener(this);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showImage() {
        ImageView imageView = (ImageView) findViewById(R.id.image_two);

        //获取屏幕宽高
        Display currentDisplay = this.getWindowManager().getDefaultDisplay();
        int widthDis = currentDisplay.getWidth();
        int heightDis = currentDisplay.getHeight();

        Log.d(TAG, "currentDisplay: width:" + widthDis + " height:" + heightDis);

        String strFilePath = mImageFilePath;
        getPhotoInfo(strFilePath);
//		File file = new File(mImageFilePath);
//		if(!file.exists()){
//			Log.d(TAG, " file is not exist");
//			return;
//		}
//		strFilePath = file.getAbsolutePath();
//		Uri uri = Uri.fromFile(file);
//		strFilePath = uri.getPath();
//		Log.d(TAG, "image file path:"+ strFilePath);	
        //获取图像尺寸
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(strFilePath, bitmapOptions);


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
        bmp = BitmapFactory.decodeFile(strFilePath, bitmapOptions);

        Log.d(TAG, "after bap width:" + bmp.getWidth() + " height:" + bmp.getHeight() /*+ " size:"+bmp.getByteCount()*/);

        imageView.setImageBitmap(bmp);
    }

    private void showImageThree() {
        try {
            ImageView imageView = (ImageView) findViewById(R.id.image_three);

            //获取屏幕宽高
            Display currentDisplay = this.getWindowManager().getDefaultDisplay();
            int widthDis = currentDisplay.getWidth();
            int heightDis = currentDisplay.getHeight();

            Log.d(TAG, "currentDisplay: width:" + widthDis + " height:" + heightDis);


            //获取图像尺寸
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(mImageUri), null, bitmapOptions);


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
            bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri), null, bitmapOptions);

            Log.d(TAG, "after bap width:" + bmp.getWidth() + " height:" + bmp.getHeight() /*+ " size:"+bmp.getByteCount()*/);

            imageView.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE_CAMEAR_ONE) {
            if (resultCode == RESULT_OK) {
                try {
                    //直接从系统的拍照应用只有显示一个很小的缩略图。
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.image_one);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CODE_CAMEAR_TWO) {
            if (resultCode == RESULT_OK) {
                showImage();
            }
        } else if (requestCode == REQUEST_CODE_CAMEAR_THREE) {
            if (resultCode == RESULT_OK) {
                showImageThree();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, TAG + "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, TAG + "onclick backimg");
                finish();
                break;
            }
            case R.id.btn_one: {
                //直接从拍照意图打开系统内置的拍照应用进行拍照
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMEAR_ONE);
                break;
            }
            case R.id.btn_two: {
                mImageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/camera_demo_two.jpg";
                File file = new File(mImageFilePath);
                Uri imageUri = Uri.fromFile(file);
                mImageFilePath = imageUri.getPath();
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CODE_CAMEAR_TWO);
                break;
            }
            case R.id.btn_three: {
                mImageUri = this.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(intent, REQUEST_CODE_CAMEAR_THREE);
                break;
            }

        }
    }
}

