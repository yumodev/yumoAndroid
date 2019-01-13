package com.yumo.android.test.media.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.yumo.android.common.utils.DownImageUtils;
import com.yumo.common.media.YmImageUtil;
import com.yumo.demo.config.Config;
import com.yumo.demo.view.YmTestFragment;

public class TestDownloadImageView extends YmTestFragment {
    public void testDownloadAndShowImage(){
        final String url = "http://www.zebrai.cn/login/picture/getCaptcha";
        //final String url = "https://ict.image.alimmdn.com/banma_h5/zebrai/zebra_login_bg.png";
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
        Log.d(Config.LOG_TAG,  "width:" + options.outWidth + " height:" + options.outHeight + " MiniType:" + options.outMimeType);

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);

        imageView.setImageBitmap(bitmap);
        showTestView(imageView);
    }
}
