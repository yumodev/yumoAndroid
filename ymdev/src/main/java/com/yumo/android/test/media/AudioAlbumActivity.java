/**
 * AudioAlbum.java
 * yumodev
 * 2015-1-24
 * 本地音频列表，进入子目录，并且进行播放
 */
package com.yumo.android.test.media;

import java.io.File;

import com.yumo.android.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * yumodev
 *
 */
public class AudioAlbumActivity extends ListActivity implements View.OnClickListener {
    private final String TAG = AudioAlbumActivity.class.getSimpleName() + "  ";

    //在专辑列表
    private final int PAGE_ALBUM = 0;
    //在音频列表页面
    private final int PAGE_MEDIA = 1;

    private int mCurrentPage = PAGE_ALBUM;

    /**
     * 专辑的游标
     */
    private Cursor mCursor = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.audio_album_page);
        initView();
    }

    /**
     * 初始化界面
     * yumodev
     * @return
     * boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("AudioAlbum");

        showAlums();

        return true;
    }

    /**
     * 现实专辑
     * yumodev
     * void
     * 2015-1-24
     */
    public void showAlums() {
        String[] columns = {android.provider.MediaStore.Audio.Albums._ID,
                android.provider.MediaStore.Audio.Albums.ALBUM};

        mCursor = managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                columns, null, null, null);
        Log.d(TAG, "cursor count:" + mCursor.getCount());
        int idColums = mCursor
                .getColumnIndex(android.provider.MediaStore.Audio.Albums._ID);
        int albumColums = mCursor
                .getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM);

        while (mCursor.moveToNext()) {
            Log.d(TAG,
                    mCursor.getString(idColums) + "  "
                            + mCursor.getString(albumColums));
        }

        mCursor.moveToFirst();

        String[] displayFields = new String[]{android.provider.MediaStore.Audio.Albums.ALBUM};
        int[] displayViews = new int[]{android.R.id.text1};
        setListAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, mCursor, displayFields,
                displayViews));
        mCurrentPage = PAGE_ALBUM;
    }

    /**
     * 现实专辑
     * yumo
     * void
     * 2015-1-24
     */
    public void showMedia(String strAlbum) {
        String[] columns = {
                android.provider.MediaStore.Audio.Media.TITLE,
                android.provider.MediaStore.Audio.Media._ID,
                android.provider.MediaStore.Audio.Media.MIME_TYPE,
                android.provider.MediaStore.Audio.Media.DATA,

                android.provider.MediaStore.Audio.Media.DISPLAY_NAME};

        String strWhere = android.provider.MediaStore.Audio.Media.ALBUM + " = ? ";
        String[] strWhereVal = {strAlbum};
        String strOrderBy = android.provider.MediaStore.Audio.Media.TITLE;

        mCursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                columns, strWhere, strWhereVal, strOrderBy);
        Log.d(TAG, "cursor count:" + mCursor.getCount());
//		int idColums = mCursor
//				.getColumnIndex(android.provider.MediaStore.Audio.Albums._ID);
//		int albumColums = mCursor
//				.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM);
//
//		while (mCursor.moveToNext()) {
//			Log.d(TAG,
//					mCursor.getString(idColums) + "  "
//							+ mCursor.getString(albumColums));
//		}

        mCursor.moveToFirst();

        String[] displayFields = new String[]{android.provider.MediaStore.Audio.Media.DISPLAY_NAME};
        int[] displayViews = new int[]{android.R.id.text1};
        setListAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, mCursor, displayFields,
                displayViews));

        mCurrentPage = PAGE_MEDIA;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //进入音频列表
        if (mCurrentPage == this.PAGE_ALBUM) {
            mCursor.moveToPosition(position);
            String strAlbum = mCursor.getString(mCursor.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM));
            showMedia(strAlbum);
        } else {
            //播放音频
            int filePathIndex = mCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int mineTypeIndex = mCursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);

            mCursor.moveToPosition(position);
            String filePath = mCursor.getString(filePathIndex);
            String mineType = mCursor.getString(mineTypeIndex);

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), mineType);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, TAG + "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, TAG + "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, TAG + "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, TAG + "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, TAG + "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, TAG + "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, TAG + "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, TAG + "onclick backimg");
                if (mCurrentPage == this.PAGE_MEDIA) {
                    showAlums();
                } else {
                    finish();
                }
                break;
            }
        }
    }
}
