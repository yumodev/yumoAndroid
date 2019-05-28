package com.yumo.android.test.view.recyclerview.nestedscroll;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
