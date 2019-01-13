package com.yumo.android.test.net.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yumo.android.test.net.leancloud.LeanCloudDefine;
import com.yumo.android.test.net.leancloud.LeanCloudHelper;
import com.yumo.android.test.entry.User;
import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yumodev on 17/1/10.
 */

public class TestRetrofitFragment extends YmTestFragment {
    private final String LOG_TAG = "TestRetrofitFragment";

    private Retrofit mRetrofit = null;
    private TestApiInterface mService = null;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message) {
                Log.i("RetrofitLog", "retrofitBack="+message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

//        OkHttpClient httpClient = new OkHttpClient();
//        httpClient.newBuilder().addInterceptor(logging);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(LeanCloudDefine.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        mService = mRetrofit.create(TestApiInterface.class);
    }

    public void testRegitster(){
        OkHttpClient httpClient = new OkHttpClient();

        // 统一添加的Header
        httpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                YmOkHttpUtil.appendHeaders(builder, LeanCloudHelper.getCommonHeader());
                Request request = builder.build();
                return chain.proceed(request);
            }
        });


        // log
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.interceptors().add(interceptor);

        User user = new User();
        user.username = "test1234";
        user.password = "ddddddd";
        user.phone = "11111111111";
        Call<User> register = mService.register(user, LeanCloudHelper.getCommonHeader());
        register.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response != null){
                    Log.i(LOG_TAG, "register:"+response.toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(LOG_TAG, "register:"+t.getMessage());
            }
        });
    }

    public void testLogin(){
        User user = new User();
        user.username = "test1234";
        user.password = "ddddddd";
        Call<User> login = mService.login(user.username, user.password, LeanCloudHelper.getCommonHeader());
        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response != null){
                    Log.i(LOG_TAG, "register:"+response.toString() + " message:"+response.message());
                    if (response.body() != null){
                        Log.i(LOG_TAG, response.body().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(LOG_TAG, "register:"+t.getMessage());
            }
        });
    }
}
