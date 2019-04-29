package com.yumodev.retrofit.ganio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.retrofit.Define;
import com.yumodev.retrofit.ganio.entry.DayResult;
import com.yumodev.retrofit.ganio.entry.GanItem;
import com.yumodev.retrofit.ganio.entry.GanResult;
import com.yumodev.retrofit.ganio.entry.GanSearchResult;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yumodev on 17/12/14.
 */

public class GanIoTest extends YmTestFragment {
    public static final String LOG_TAG = Define.LOG_TAG_GANIO;
    Retrofit mRetrofit;
    GanIoService mService;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(GanIoService.class);
    }

    public void test(){
        showToastMessage("test");
    }

    /**
     * 获取历史API
     */
    public void testHistoryResponse(){
        mService.historyResponse().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.i(LOG_TAG, "onResponse:"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(LOG_TAG, "onFailure:"+t.getMessage());
            }
        });
    }

    /**
     * 获取历史API
     */
    public void testHistory(){
        mService.history().enqueue(new Callback<GanResult<List<String>>>() {
            @Override
            public void onResponse(Call<GanResult<List<String>>> call, Response<GanResult<List<String>>> response) {
                Log.i(LOG_TAG, response.body().results.toString());
            }

            @Override
            public void onFailure(Call<GanResult<List<String>>> call, Throwable t) {
                Log.i(LOG_TAG, "onFailure:"+t.getMessage());
            }
        });
    }

    /**
     * 获取数据
     */
    public void testDataCategoryResonse(){
        mService.dataCategoryResonse("Android", 10, 1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.i(LOG_TAG, "onResponse:"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(LOG_TAG, "onFailure:"+t.getMessage());
            }
        });
    }

    public void testData(){
        fetchData("Android", 10, 1);
    }

    private void fetchData(String category, int pageNum, int page){
        mService.dataCategory(category, pageNum, page).enqueue(new Callback<GanResult<List<GanItem>>>() {
            @Override
            public void onResponse(Call<GanResult<List<GanItem>>> call, Response<GanResult<List<GanItem>>> response) {
                Log.i(LOG_TAG, "onResponse:"+response.body().results.size());

                for (GanItem item : response.body().results){
                    Log.i(LOG_TAG, (new Gson()).toJson(item));
                }
            }

            @Override
            public void onFailure(Call<GanResult<List<GanItem>>> call, Throwable t) {
                Log.i(LOG_TAG, "onFailure:"+t.getMessage());
            }
        });
    }


    public void testSearch(){
        search("Android", 50 ,1);
    }

    private void search(String category, int count, int page){
        mService.search(category, count, page).enqueue(new Callback<GanSearchResult<List<GanItem>>>() {
            @Override
            public void onResponse(Call<GanSearchResult<List<GanItem>>> call, Response<GanSearchResult<List<GanItem>>> response) {
                Log.i(LOG_TAG, "count:"+response.body().count);
                for (GanItem item : response.body().results){
                    Log.i(LOG_TAG, (new Gson()).toJson(item));
                }
            }

            @Override
            public void onFailure(Call<GanSearchResult<List<GanItem>>> call, Throwable t) {

            }
        });
    }

    public void testDay(){
        fetchDay("2017/12/11");
    }

    private void fetchDay(String day){
        mService.day(day).enqueue(new Callback<DayResult>() {
            @Override
            public void onResponse(Call<DayResult> call, Response<DayResult> response) {
                Log.i(LOG_TAG, "count:"+response.body().category.toString());
                for (String category : response.body().category){
                    if (category.equals("Android")){
                        for (GanItem item : response.body().results.androidData){
                            Log.i(LOG_TAG, (new Gson()).toJson(item));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DayResult> call, Throwable t) {

            }
        });
    }
}
