package com.yumo.android.test.architecture;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.yumo.android.test.architecture.model.ModleTest;
import com.yumo.demo.view.YmTestFragment;

public class TestViewModel extends YmTestFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModleTest testViewModel = ViewModelProviders.of(this).get(ModleTest.class);
    }
}
