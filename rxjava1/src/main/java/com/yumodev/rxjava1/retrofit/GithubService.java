package com.yumodev.rxjava1.retrofit;

import com.yumodev.rxjava1.retrofit.entry.RootGithubUrl;
import com.yumodev.rxjava1.retrofit.entry.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yumo on 2018/6/26.
 */

public interface GithubService {
    @GET
    Call<RootGithubUrl> getRootGithubUrls(@Url String url);

    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);

    @GET("users/{user}/following")
    Call<List<User>> getUserFollowing(@Path("user") String user);

    @GET("users/{user}/following")
    Observable<List<User>> getUserFollowingObservable(@Path("user") String user);

}
