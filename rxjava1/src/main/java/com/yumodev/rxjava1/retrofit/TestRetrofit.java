package com.yumodev.rxjava1.retrofit;

import com.google.gson.Gson;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.rxjava1.retrofit.entry.RootGithubUrl;
import com.yumodev.rxjava1.retrofit.entry.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yumo on 2018/6/26.
 * [Retrofit结合RxJava使用指南](https://www.cnblogs.com/mengdd/p/6047948.html)
 */

public class TestRetrofit extends YmTestFragment {

    public void testRootGithubUrls(){
        GithubService githubService = ServiceGenerator.createService(GithubService.class);
        Call<RootGithubUrl> rootGithubUrlCalls = githubService.getRootGithubUrls("");
        rootGithubUrlCalls.enqueue(new Callback<RootGithubUrl>() {
            @Override
            public void onResponse(Call<RootGithubUrl> call, Response<RootGithubUrl> response) {
                RootGithubUrl rootGithubUrl = response.body();
                showToastMessage(rootGithubUrl.getRepository_url());
            }

            @Override
            public void onFailure(Call<RootGithubUrl> call, Throwable t) {

            }
        });
    }


    /**
     * 获取用户信息
     */
    public void testPrintUserInfo(){
        GithubService githubService = ServiceGenerator.createService(GithubService.class);
        Call<User> userCall = githubService.getUser("yumodev");
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                showToastMessage((new Gson()).toJson(user));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    /**
     * 获取用户信息
     */
    public void testPrintUserFollowing(){
        GithubService githubService = ServiceGenerator.createService(GithubService.class);
        Call<List<User>> userCall = githubService.getUserFollowing("yumodev");
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> user = response.body();
                showToastMessage((new Gson()).toJson(user));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    public void testPrintUserFollowingByRxjava(){
        GithubService githubService = ServiceGenerator.createService(GithubService.class);
        githubService.getUserFollowingObservable("yumodev")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<User> users) {
                        showToastMessage((new Gson()).toJson(users));
                    }
                });

    }
}
