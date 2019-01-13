package com.yumo.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.yumo.ui.fragment.FragmentInfo;
import com.yumo.ui.fragment.ISelectFragment;

/**
 * Created by yumodev on 2/3/16.
 */
public class YmActivity extends AppCompatActivity implements ISelectFragment {

    protected FragmentInfo mCurrentFragmentInfo;

    @Override
    public void setSelectFragment(FragmentInfo fragmentInfo) {
        mCurrentFragmentInfo = fragmentInfo;
    }


}
