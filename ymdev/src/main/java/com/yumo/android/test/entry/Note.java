package com.yumo.android.test.entry;


import android.text.TextUtils;

import com.yumo.common.util.YmUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by yumodev on 17/3/7.
 */

public class Note {
    public static short STATUS_ADD = 1;
    public String id;//objectId
    public String title;
    public int fileSize;
    public String summary;
    public String hash;
    public long createTime;
    public long updateTime;
    public String content;
    public short status = STATUS_ADD;
    public int ver;


    public static Note createNote(String id, String title){
        Note note = new Note();

        if (TextUtils.isEmpty(id)){
            note.id = YmUtil.createUUID();
        }
        note.title = title;
        note.status = 1;
        return note;
    }

    public static JSONObject convertNoteToJson(Note note){

        JSONObject json = new JSONObject();
        try {
            if (note.status != STATUS_ADD){
                json.put("objectid", note.id);
            }

            json.put("title", note.title);

            if (note.status != STATUS_ADD){
                json.put("createAt", note.createTime);
            }
            json.put("summary", note.summary);
            json.put("fileSize", note.fileSize);
            json.put("hash", note.hash);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
