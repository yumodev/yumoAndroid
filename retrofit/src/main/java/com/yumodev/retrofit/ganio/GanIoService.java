package com.yumodev.retrofit.ganio;

import com.yumodev.retrofit.ganio.entry.DayResult;
import com.yumodev.retrofit.ganio.entry.GanItem;
import com.yumodev.retrofit.ganio.entry.GanResult;
import com.yumodev.retrofit.ganio.entry.GanSearchResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yumodev on 17/12/14.
 */

public interface GanIoService {

    String BASE_URL = "http://www.gank.io/api/";

    /**
     * 获取发过干货的日期
     * @return
     */
    @GET("day/history")
    Call<ResponseBody> historyResponse();

    @GET("day/history")
    Call<GanResult<List<String>>> history();


    /**
     * 获取所有干货
     * @param category ： all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param pageNum
     * @param page
     * @return
     */
    @GET("data/{category}/{pagenum}/{page}")
    Call<ResponseBody> dataCategoryResonse(@Path("category") String category, @Path("pagenum") int pageNum, @Path("page") int page);

    @GET("data/{category}/{pagenum}/{page}")
    Call<GanResult<List<GanItem>>> dataCategory(@Path("category") String category, @Path("pagenum") int pageNum, @Path("page") int page);


    /**
     * 查询干货IO
     * @param category
     * @param count
     * @param page
     * @return
     */
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    Call<GanSearchResult<List<GanItem>>> search(@Path("category") String category, @Path("count") int count, @Path("page") int page);


    @GET("day/{day}")
    Call<DayResult> day(@Path("day") String day);
}
