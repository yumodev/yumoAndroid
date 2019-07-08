package com.yumo.demo.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yumo.demo.R;

/**
 * Created by yumodev on 17/2/17.
 */

public abstract class YmDemoBaseFragment extends Fragment {
    protected Toolbar mToolbar = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.base_fragment, null);
        mToolbar = rootView.findViewById(R.id.toolbar);

        View containerView = getContainerView(inflater, container, savedInstanceState);
        if (containerView != null){
            rootView.addView(containerView);
        }
        return rootView;
    }

    abstract protected View getContainerView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 获取标题
     * @return
     */
    public Toolbar getToolbar(){
        return mToolbar;
    }

    public void setToolbar(Toolbar toolbar){
        mToolbar = toolbar;
    }


}
