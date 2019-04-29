package com.yumodev.retrofit.ganio.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yumodev on 17/12/15.
 */

public class DayResult {
    public List<String> category;
    public Result results;
    public boolean error;


    public class Result{

        @SerializedName("Android")
        public List<GanItem> androidData;
        @SerializedName("App")
        public List<GanItem> appData;
        @SerializedName("福利")
        public List<GanItem> meiziData;
        @SerializedName("休息视频")
        public List<GanItem> videoData;
        @SerializedName("扩展资源")
        public List<GanItem> resourceData;
        @SerializedName("瞎推荐")
        public List<GanItem> recommendData;
        @SerializedName("前端")
        public List<GanItem> webData;
        @SerializedName("ios")
        public List<GanItem> ioaData;
    }
}
