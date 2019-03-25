package com.yumo.android.test.architecture;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumo.android.test.architecture.net.NetChangeModel;
import com.yumo.common.net.YmNetType;
import com.yumo.common.net.YmNetUtils;
import com.yumo.demo.view.YmTestFragment;

public class TestArch extends YmTestFragment {

    private TextView mNetWorkTypeView = null;
    private NetChangeModel mNetChangeModel = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetChangeModel = ViewModelProviders.of(this).get(NetChangeModel.class);
        getLifecycle().addObserver(mNetChangeModel);
        final Observer<YmNetType> netChangeObserver = new Observer<YmNetType>() {
            @Override
            public void onChanged(@Nullable YmNetType ymNetType) {
                mNetWorkTypeView.setText(ymNetType.name());
            }
        };

        mNetChangeModel.getNetType().observe(this, netChangeObserver);
    }


    @Override
    protected View getHeaderView() {
        return createView();
    }

    private LinearLayout createView(){
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        mNetWorkTypeView = new TextView(getContext());
        linearLayout.addView(mNetWorkTypeView);

        mNetChangeModel.init(YmNetUtils.getNetworkType(getContext()));

        return linearLayout;
    }
}
