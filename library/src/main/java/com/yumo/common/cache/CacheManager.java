package com.yumo.common.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.io.YmAdFileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by yumodev on 17/9/9.
 * 图片缓存架构
 */
public class CacheManager {
    private LruCache<String, Bitmap> mLruCache;
    private DiskLruCache mDiskLruCache;
    private CacheManager(){

    }

    public static CacheManager getInstance(){
        return CacheManagerHolder.mInstance;
    }

    public void init(Context context){
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 8;
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        //获取缓存路径，设置缓存大小
        try {
            File cacheFile = new File(YmAdFileUtil.getFileCache(context, "cache"));
            if (!cacheFile.exists()) cacheFile.mkdirs();
            mDiskLruCache = DiskLruCache.open(cacheFile, YmAppUtil.getAppVersion(context), 1, Long.valueOf(maxSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(String key){
        Bitmap bmp = null;
        if (mLruCache != null){
            bmp = mLruCache.get(key);
        }

        if (mDiskLruCache != null && bmp != null){

        }
        return null;
    }

    public boolean putBitmap(String key){
        return false;
    }

    public Bitmap remove(String key){
        return null;
    }

    public void clearAll(){
        if (mLruCache != null){
            mLruCache.evictAll();
        }

        if (mDiskLruCache != null){
            try {
                mDiskLruCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class CacheManagerHolder{
        private static final CacheManager mInstance = new CacheManager();
    }
}
