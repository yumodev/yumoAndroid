package com.yumo.android.test.net.retrofit;

import com.yumo.android.test.entry.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yumodev on 17/1/10.
 */

public interface TestApiInterface {
    // 注册用户
    @POST("/1.1/users")
    Call<User> register(
            @Body User user, @HeaderMap Map<String, String> headers);

    // 登录用户
    @GET("/1/login")
    Call<User> login(
            @Query("username") String username,
            @Query("password") String password,
            @HeaderMap Map<String, String > header);
}
