package com.yumodev.retrofit.github;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.yumo.demo.view.YmTestFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class GithubTest extends YmTestFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<ResponseBody> repos = service.listRepos("yumodev");
    }
}
