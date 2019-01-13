/**
 * BitmapLruCache.java
 * yumodev
 * 2015-1-17
 */
package com.yumo.common.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;


/**
 * yumodev
 * 封装的一个BitmapLru缓存
 */
public class BitmapLruCache {
    private static final String TAG = "Bitmap";
    private LruCache<String, Bitmap> mCache;


    public BitmapLruCache(){
        //获取App最大的内存的1/8做LRU的内存缓存
        this((int) Runtime.getRuntime().maxMemory() / 8);
    }

    public BitmapLruCache(int maxSize) {
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 从LRU缓存中获取图片
     * @param key
     * @return
     */
    public Bitmap getBitmap(String key) {
        Log.i(TAG, "get Bitmap :" + key);
        return mCache.get(key);
    }

    /**
     * 保存图片到Lru缓存中。
     * @param key
     * @param bitmap
     */
    public void putBitmap(String key, Bitmap bitmap) {
        Log.i(TAG, "put Bitmap :" + key);
        mCache.put(key, bitmap);
    }

    /**
     * 重新设置总缓存的大小
     * @param maxSize
     */
    public void resize(int maxSize){
        Log.i(TAG, "resize");
        mCache.resize(maxSize);
    }
}
