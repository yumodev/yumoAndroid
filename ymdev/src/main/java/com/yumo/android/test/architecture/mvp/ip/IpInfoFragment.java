package com.yumo.android.test.architecture.mvp.ip;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.android.R;

/**
 * Created by yumodev on 17/9/7.
 */

public class IpInfoFragment extends Fragment implements IpInfpContract.View {

    private TextView mCountryTv;
    private TextView mAreaTv;
    private TextView mCityTv;
    private EditText mIpEt;
    private Button mIpSearchBtn;

    private IpInfpContract.Presenter mPresenter;

    public static IpInfoFragment newInstance(){
        return new IpInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.ipinfo, container, false);
        mCountryTv = (TextView)root.findViewById(R.id.ip_country);
        mAreaTv = (TextView)root.findViewById(R.id.ip_area);
        mCityTv = (TextView)root.findViewById(R.id.ip_city);
        mIpEt = (EditText) root.findViewById(R.id.ip_et);
        mIpSearchBtn = (Button)root.findViewById(R.id.ip_search_btn);

        mIpSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String ip = mIpEt.getText().toString();
                if (TextUtils.isEmpty(ip)){
                    Toast.makeText(getContext(), "ip不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                mPresenter.getIoInfo(ip);
            }
        });

        return root;
    }

    @Override
    public void setPresenter(IpInfpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
