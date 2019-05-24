/**
 * PhotoWallDemoActivity.java
 * yumodev
 * 2015-1-18
 * 一个瀑布流的例子，代码参照http://blog.csdn.net/guolin_blog/article/details/10470797
 */
package com.yumo.android.test.media.image;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import com.yumo.common.android.YmAppUtil;
import com.yumo.common.cache.DiskLruCache;
import com.yumo.common.cache.DiskLruCache.Snapshot;

import com.yumo.android.R;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.media.YmImageUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.collection.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * yumodev
 */
public class PhotoWallDemoActivity extends Activity implements View.OnClickListener {
    private final String TAG = PhotoWallDemoActivity.class.getSimpleName() + "  ";

    /**
     * 存放图片的GridView
     */
    private GridView mGridView = null;
    private PhotoWallAdapter mAdapter = null;

    /**
     * 图片的高度
     */
    private int mImageThumbSize;

    /**
     * 图片的间距
     */
    private int mImageThumbSpacing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_wall_demo_page);
        initView();
    }

    /**
     * yumo
     * @return boolean
     * 2014-12-3
     */
    private void initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("PhtotWall");

        mGridView = (GridView) findViewById(R.id.gridview);

        mAdapter = new PhotoWallAdapter(mGridView, Images.imageThumbUrls, this);
        mGridView.setAdapter(mAdapter);

        mImageThumbSize = getResources().getDimensionPixelSize(
                R.dimen.photo_wall_image_height);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
                R.dimen.photo_wall_image_vertiacl_space);

        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        Log.d(TAG, "begin onGloballLayout");
                        //获取显示的列数
                        final int numColumns = (int) Math.floor(mGridView
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));

                        if (numColumns > 0) {
                            int columnWidth = (mGridView.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            mAdapter.setItemHeight(columnWidth);
                            mGridView.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                            Log.d(TAG, "onGlobalLayout:height:" + columnWidth);
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        mAdapter.fluchCache();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        mAdapter.cancelAllTasks();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, "onclick backimg");
                finish();
                break;
            }
        }
    }

    public class PhotoWallAdapter extends BaseAdapter {
        private final String TAG = PhotoWallAdapter.class.getSimpleName();
        private String[] mImages;

        /**
         * 硬盘缓存
         */
        private DiskLruCache mDiskLruCache = null;

        /**
         * Lru缓存
         */
        private LruCache<String, Bitmap> mLruCache = null;

        private Context mContext = null;

        /**
         * 存储图片下载的任务
         */
        private Set<BitmapTask> taskCollection;

        /**
         * 每一列的高度
         */
        private int mItemHeight = 0;

        public PhotoWallAdapter(GridView gridView, String[] images, Context context) {
            //this.mGridView = gridView;
            mImages = images;
            mContext = context;
            taskCollection = new HashSet<BitmapTask>();

            //设置内存缓存的大小
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 8;

            mLruCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getHeight() * bitmap.getRowBytes();
                }
            };

            //获取缓存路径，设置缓存大小
            try {
                File cacheFile = new File(YmAdFileUtil.getFileCache(context, "thumb"));
                if (!cacheFile.exists()) cacheFile.mkdirs();
                mDiskLruCache = DiskLruCache.open(cacheFile, YmAppUtil.getAppVersion(context), 1, Long.valueOf(cacheSize));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getCount() {
            if (mImages != null) return mImages.length;
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.imageview_item, null);
            } else view = convertView;

            final ImageView imageView = (ImageView) view.findViewById(R.id.image_id);

            if (imageView.getLayoutParams().height != mItemHeight) {
                imageView.getLayoutParams().height = mItemHeight;
            }

            imageView.setTag(mImages[position]);
            imageView.setImageResource(R.drawable.empty_photo);
            loadBitmaps(imageView, mImages[position]);
            return view;
        }

        public void setItemHeight(int height) {
            if (height == mItemHeight) {
                return;
            }
            mItemHeight = height;
            notifyDataSetChanged();
        }

        public void loadBitmaps(ImageView imageView, String imageUrl) {
            //Log.d("PhotoWallAdapter","loadBitmapsimageUrl:"+imageUrl);
            try {
                Bitmap bitmap = getLruCacheBitmap(imageUrl);
                if (bitmap == null) {
                    BitmapTask task = new BitmapTask();
                    taskCollection.add(task);
                    task.execute(imageUrl);
                } else {
                    Log.d(TAG, " has bitmap");
                    if (imageView != null && bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        Log.d(TAG, " has bitmap show");
                        notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Bitmap getLruCacheBitmap(String key) {
            return mLruCache.get(key);
        }

        public void putLruCacheBitmap(Bitmap bmp, String key) {
            if (getLruCacheBitmap(key) == null) mLruCache.put(key, bmp);
        }

        /**
         * 使用MD5算法对传入的key进行加密并返回。
         */
        public String hashKeyForDisk(String key) {
            String cacheKey;
            try {
                final MessageDigest mDigest = MessageDigest.getInstance("MD5");
                mDigest.update(key.getBytes());
                cacheKey = bytesToHexString(mDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                cacheKey = String.valueOf(key.hashCode());
            }
            return cacheKey;
        }

        /**
         * 将缓存记录同步到journal文件中。
         */
        public void fluchCache() {
            if (mDiskLruCache != null) {
                try {
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * yumo
         *
         * @param bytes
         * @return String
         * 2015-3-14
         */
        private String bytesToHexString(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        }

        /**
         * 取消所有的下载任务
         * yumo
         * void
         * 2015-3-14
         */
        public void cancelAllTasks() {
            if (taskCollection != null) {
                for (BitmapTask task : taskCollection) {
                    task.cancel(false);
                }
            }
        }

        /**
         * yumo
         * 图片下载的异步处理，先从缓存中查找图片，如果找不到在从网络上下载。
         */
        class BitmapTask extends AsyncTask<String, Void, Bitmap> {

            String imageUrl;

            @Override
            protected Bitmap doInBackground(String... params) {
                imageUrl = params[0];

                FileInputStream fileInputStream = null;
                FileDescriptor fileDescriptor = null;
                Snapshot snapShot = null;

                final String key = hashKeyForDisk(imageUrl);

                try {
                    snapShot = mDiskLruCache.get(key);

                    if (snapShot == null) {
                        DiskLruCache.Editor editer = mDiskLruCache.edit(key);
                        if (editer != null) {
                            OutputStream outputStream = editer.newOutputStream(0);
                            if (YmImageUtil.saveImageToStream(imageUrl, outputStream)) {
                                editer.commit();
                            } else {
                                editer.abort();
                            }
                        }
                        snapShot = mDiskLruCache.get(key);
                    }

                    if (snapShot != null) {
                        fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                        fileDescriptor = fileInputStream.getFD();
                    }

                    Bitmap bmp = null;

                    if (fileDescriptor != null) {
                        bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                    }

                    if (bmp != null) {
                        putLruCacheBitmap(bmp, imageUrl);
                    }

                    return bmp;

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileInputStream != null)
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap == null) {
                    Log.d(TAG, "onPostExecute bitmap == null");
                }

                // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
                ImageView imageView = (ImageView) mGridView.findViewWithTag(imageUrl);
                Log.d(TAG, "onPostExecute");
                if (imageView == null) {
                    Log.d(TAG, "onPostExecute imageView == null");
                }
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);

                    Log.d(TAG, " has bitmap show1");
                }

                taskCollection.remove(this);

            }

        }

    }
}