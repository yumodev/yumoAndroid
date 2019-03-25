package com.yumo.android.test.media.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yumo.android.R;
import com.yumo.android.test.media.image.BitmapBlurUtil;
import com.yumo.common.log.Log;
import com.yumo.common.media.YmImageUtil;

public class BlurView extends LinearLayout {
    private static final String TAG = "BlurView";
    public BlurView(Context context) {
        super(context);

        setBackgroundResource(R.drawable.layout1_bg);
        setOrientation(VERTICAL);

        createImageBlur();
        createArcImageBlur();
        createArcImageBlur1();
        createBitmapBlur();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private void createImageBlur(){
        ImageView imageView = new ImageView(getContext());
        Bitmap bitmap = YmImageUtil.createCircleBitmapFromColor(Color.parseColor("#00c4cb"), 72);
       // bitmap = rsBlur(getContext(), bitmap, 5, 1);
        // bitmap = getTransparentBitmap(bitmap,20);
        imageView.setImageBitmap(bitmap);
        addView(imageView);
    }

    private void createArcImageBlur(){
        ImageView imageView = new ImageView(getContext());
        Bitmap bitmap = YmImageUtil.createCircleBitmapFromColor(Color.parseColor("#3300c4cb"), 72);
        bitmap = rsBlur(getContext(), bitmap, 25, 1);
        //bitmap = getTransparentBitmap(bitmap,20);
        imageView.setImageBitmap(bitmap);
        addView(imageView);
    }

    private void createArcImageBlur1(){
        ImageView imageView = new ImageView(getContext());
        Bitmap bitmap = YmImageUtil.createCircleBitmapFromColor(Color.parseColor("#3300c4cb"), 72);
        BitmapBlurUtil.getInstance().setImageBlurBitmap(getContext(), imageView, bitmap,1.0f, 25);
        //bitmap = getTransparentBitmap(bitmap,10);
        //imageView.setImageBitmap(bitmap);
        addView(imageView);
    }

    private void createBitmapBlur(){
        ImageView imageView = new ImageView(getContext());
        Bitmap bitmap = YmImageUtil.drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher));
        bitmap = rsBlur(getContext(), bitmap, 25, 1);
        //bitmap = getTransparentBitmap(bitmap,20);
        imageView.setImageBitmap(bitmap);
        addView(imageView);
    }

    private Bitmap rsBlur(Context context, Bitmap source, int radius, float scale){

        Log.i(TAG,"origin size:"+source.getWidth()+"*"+source.getHeight());
        int width = Math.round(source.getWidth() * scale);
        int height = Math.round(source.getHeight() * scale);
        Bitmap inputBmp = Bitmap.createScaledBitmap(source,width,height,false);

        RenderScript renderScript =  RenderScript.create(context);

        Log.i(TAG,"scale size:"+inputBmp.getWidth()+"*"+inputBmp.getHeight());

        // Allocate memory for Renderscript to work with

        final Allocation input = Allocation.createFromBitmap(renderScript,inputBmp);
        final Allocation output = Allocation.createTyped(renderScript,input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);

        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);

        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);


        renderScript.destroy();
        return inputBmp;
    }


    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number){
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

                .getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;
    }
}
