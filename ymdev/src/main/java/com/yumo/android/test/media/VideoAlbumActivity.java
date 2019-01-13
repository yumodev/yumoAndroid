/**
 * VideoAlbumActivity.java
 * yumodev
 * 2015-1-26
 */
package com.yumo.android.test.media;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yumo.android.R;
import com.yumo.android.common.widget.CustomSimpleAdapter;
import com.yumo.android.common.data.VideoInfo;
import com.yumo.android.common.inface.ListClickInterface;
import com.yumo.common.util.YmDateUtil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * yumodev
 */
public class VideoAlbumActivity extends Activity implements View.OnClickListener {

    private final String TAG = VideoAlbumActivity.class.getSimpleName() + "  ";

    private ListView mList = null;

    private CustomSimpleAdapter mAdapter = null;

    private List<VideoInfo> mVideoInfos = new ArrayList<VideoInfo>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.video_album_page);
        initView();
    }

    /**
     * 初始化界面
     * yumodev
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("VideoAlbum");

        getVideoAlbum();

        int toView[] = {R.id.image, R.id.title_text, R.id.time_text};
        String fromData[] = {"thumb", "title", "time"};
        List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();

        for (int n = 0; n < mVideoInfos.size(); n++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("thumb", mVideoInfos.get(n).mThumbPath);
            map.put("title", mVideoInfos.get(n).mTitle);
            map.put("time", YmDateUtil.formatDuration(mVideoInfos.get(n).mDuration));

            list.add(map);
        }
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new CustomSimpleAdapter(this, list, R.layout.video_album_item, fromData, toView);
        mList.setAdapter(mAdapter);

        mAdapter.setCallback(new ListClickInterface() {

            @Override
            public void onClick(int position) {
                VideoInfo videoInfo = mVideoInfos.get(position);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                File file = new File(videoInfo.mFilePath);
                intent.setDataAndType(Uri.fromFile(file), videoInfo.mMimeType);
                startActivity(intent);
            }

            @Override
            public void onClickItem(View view, int position) {
            }
        });

        return true;
    }

    private void getVideoAlbum() {
        String[] thumbColumns = {MediaStore.Video.Thumbnails.VIDEO_ID,
                MediaStore.Video.Thumbnails.DATA};

        String[] columns = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DURATION
        };

        Cursor cursor = this.managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
        Log.d(TAG, "getVideoAlbum:" + cursor.getCount());
        int idColumnsIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID);
        int dataColumnsIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
        int titleColumnsIndex = cursor.getColumnIndex(MediaStore.Video.Media.TITLE);
        int mimeTypeColumnsIndex = cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE);
        int durationColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);

        while (cursor.moveToNext()) {
            VideoInfo video = new VideoInfo();
            video.mId = cursor.getString(idColumnsIndex);
            video.mFilePath = cursor.getString(dataColumnsIndex);
            video.mTitle = cursor.getString(titleColumnsIndex);
            video.mMimeType = cursor.getString(mimeTypeColumnsIndex);
            video.mDuration = cursor.getInt(durationColumnIndex);
            String selection = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";
            String[] selectionArgs = new String[]{
                    video.mId + ""
            };
            Cursor thumbCursor = getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);
            //Cursor thumbCursor = managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "="+ video.mId, null, null);
            Log.d(TAG, "thumbCursor:nums:" + thumbCursor.getCount());
            if (thumbCursor.moveToFirst()) {
                video.mThumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
            }

            Log.d(TAG, "videoInfo:" + video.toString());

            mVideoInfos.add(video);
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
        }
    }
}

