package com.yumo.android.test.net.leancloud;

import android.util.Log;

import com.yumo.android.test.entry.Note;
import com.yumo.common.thread.YmHandlerThreadUtil;
import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;

/**
 * Created by yumodev on 17/3/7.
 * 测试笔记数据
 */

public class TestNoteLcFragment extends YmTestFragment {

    private final String LOG_TAG = "TestNoteLcFragment";

    public void testCreateNote(){
        final Note note = Note.createNote(null, "note2");
        note.fileSize = 0;
        note.hash = "";
        note.summary = "";
        YmHandlerThreadUtil.getInstance().post(new Runnable() {
            @Override
            public void run() {
                String body = Note.convertNoteToJson(note).toString();
                String result = "";
                try {
                    result = YmOkHttpUtil.postString(LeanCloudHelper.createNote(), body, LeanCloudHelper.getCommonHeader());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(LOG_TAG, "register body:"+body+" result:"+result);
                showToastMessageOnUiThread(result);
            }
        });
    }
}
