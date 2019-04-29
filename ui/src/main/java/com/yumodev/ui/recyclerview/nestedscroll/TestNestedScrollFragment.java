package com.yumodev.ui.recyclerview.nestedscroll;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yumodev.ui.R;

/**
 * Created by yumodev on 17/12/15.
 */

public class TestNestedScrollFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TestNestedScrollRecyclerView recyclerView = new TestNestedScrollRecyclerView(getContext());
        return recyclerView;
    }
}
