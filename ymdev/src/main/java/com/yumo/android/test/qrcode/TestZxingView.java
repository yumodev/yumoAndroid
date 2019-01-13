package com.yumo.android.test.qrcode;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ymdev.zxing.CaptureActivity;
import com.ymdev.zxing.CaptureLiteActivity;
import com.yumo.android.R;
import com.yumo.android.common.AppDevUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import java.util.Hashtable;

/**
 * Created by yumodev on 17/11/16.
 */

public class TestZxingView extends YmTestFragment {
    public final String LOG_TAG = Log.LIB_TAG;
    public ImageView mImageView = null;

    @Override
    public void onResume() {
        super.onResume();

        AppDevUtil.requestPermission(getActivity(), Manifest.permission.CAMERA);
    }

    @Override
    protected View getHeaderView() {
        mImageView = new ImageView(getContext());
        return mImageView;
    }

    /**
     * 生成二维码图片
     */
    public void testCreateBitmap(){
        String url = "http://m.baidu.com";
        showBitmap(createImageBitmap(url, 300, 300));
    }

    /**
     * 生成二维码图片带logo
     */
    public void testShowBitmapLogo(){
        String url = "http://m.baidu.com";
        showBitmap(createImage(url, 300, 300, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
    }

    private void showBitmap(Bitmap bitmap){
        mImageView.setImageBitmap(bitmap);
    }



    private Bitmap createImageBitmap(String text, int w, int h) {
        if (TextUtils.isEmpty(text)) return null;

        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            for (int i = 0; i < w; i++) {
                for (int y = 0; y < h; y++) {
                    if (bitMatrix.get(i, y)) {
                        pixels[i * h + y] = 0xff000000;
                    } else {
                        pixels[i * h + y] = 0xffffffff;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成二维码图片
     * @param text
     * @param w
     * @param h
     * @param logo
     * @return
     */
    public Bitmap createImage(String text,int w,int h,Bitmap logo) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }

        try {
            Bitmap scaleLogo = getScaleLogo(logo,w,h);

            int offsetX = w / 2;
            int offsetY = h / 2;

            int scaleWidth = 0;
            int scaleHeight = 0;
            if (scaleLogo != null) {
                scaleWidth = scaleLogo.getWidth();
                scaleHeight = scaleLogo.getHeight();
                offsetX = (w - scaleWidth) / 2;
                offsetY = (h - scaleHeight) / 2;
            }
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if(x >= offsetX && x < offsetX + scaleWidth && y>= offsetY && y < offsetY + scaleHeight){
                        int pixel = scaleLogo.getPixel(x-offsetX,y-offsetY);
                        if(pixel == 0){
                            if(bitMatrix.get(x, y)){
                                pixel = 0xff000000;
                            }else{
                                pixel = 0xffffffff;
                            }
                        }
                        pixels[y * w + x] = pixel;
                    }else{
                        if (bitMatrix.get(x, y)) {
                            pixels[y * w + x] = 0xff000000;
                        } else {
                            pixels[y * w + x] = 0xffffffff;
                        }
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h,Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap getScaleLogo(Bitmap logo,int w,int h){
        if(logo == null)return null;
        Matrix matrix = new Matrix();
        float scaleFactor = Math.min(w * 1.0f / 5 / logo.getWidth(), h * 1.0f / 5 /logo.getHeight());
        matrix.postScale(scaleFactor,scaleFactor);
        Bitmap result = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),   logo.getHeight(), matrix, true);
        return result;
    }

    public static void testDecodeQrCode(){
//        String filePath = "D://zxing.png";
//        BufferedImage image;
//        try {
//            image = ImageIO.read(new File(filePath));
//            LuminanceSource source = new BufferedImageLuminanceSource(image);
//            Binarizer binarizer = new HybridBinarizer(source);
//            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
//            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
//            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
//            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
//            Log.i(Log.LIB_LOG, result.getText());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

//    /**
//     * 解析二维码图片工具类
//     * @param analyzeCallback
//     */
//    public static void analyzeBitmap(String path) {
//
//        /**
//         * 首先判断图片的大小,若图片过大,则执行图片的裁剪操作,防止OOM
//         */
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 先获取原大小
//        Bitmap mBitmap = BitmapFactory.decodeFile(path, options);
//        options.inJustDecodeBounds = false; // 获取新的大小
//
//        int sampleSize = (int) (options.outHeight / (float) 400);
//
//        if (sampleSize <= 0)
//            sampleSize = 1;
//        options.inSampleSize = sampleSize;
//        mBitmap = BitmapFactory.decodeFile(path, options);
//
//        MultiFormatReader multiFormatReader = new MultiFormatReader();
//
//        // 解码的参数
//        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
//        // 可以解析的编码类型
//        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
//        if (decodeFormats == null || decodeFormats.isEmpty()) {
//            decodeFormats = new Vector<BarcodeFormat>();
//
//            // 这里设置可扫描的类型，我这里选择了都支持
//            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
//            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
//            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
//        }
//        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
//        // 设置继续的字符编码格式为UTF8
//        // hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
//        // 设置解析配置参数
//        multiFormatReader.setHints(hints);
//
//        // 开始对图像资源解码
//        Result rawResult = null;
//        try {
//            rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(mBitmap))));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (rawResult != null) {
//            if (analyzeCallback != null) {
//                analyzeCallback.onAnalyzeSuccess(mBitmap, rawResult.getText());
//            }
//        } else {
//            if (analyzeCallback != null) {
//                analyzeCallback.onAnalyzeFailed();
//            }
//        }
//    }

    public void testCapturActivity(){
        startActivity(new Intent(getActivity(), CaptureActivity.class));
    }

    public void testCapturLiteActivity(){
        startActivity(new Intent(getActivity(), CaptureLiteActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showToastMessage("onActivityResult");
    }
}
