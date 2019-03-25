package com.yumo.android.test.architecture;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yumo.android.test.architecture.model.ModleTest;
import com.yumo.demo.view.YmTestFragment;

public class TestViewModel extends YmTestFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModleTest testViewModel = ViewModelProviders.of(this).get(ModleTest.class);
    }
}
