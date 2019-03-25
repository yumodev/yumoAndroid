package com.yumo.android.test.view.radio;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.yumo.android.R;
import com.yumo.ui.fragment.YmV4Fragment;

public class RadioFragment extends YmV4Fragment {

    private RadioGroup mRadioGroup;
    private RadioGroup mVerRadioGroup;
    private HorizontalScrollView mHorScrollView;
    private ScrollView mVerScrollView;
    private int mLastOrientation;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.radiobutton_test, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        mRadioGroup = (RadioGroup) view.findViewById(R.id.hor_radiogroup_id);
        mVerRadioGroup = (RadioGroup) view.findViewById(R.id.ver_radiogroup_id);
        mHorScrollView = view.findViewById(R.id.hor_scroll);
        mVerScrollView = view.findViewById(R.id.ver_scroll);
        mLastOrientation = getResources().getConfiguration().orientation;
        changeOrigin(mLastOrientation);


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton bt = (RadioButton) group.findViewById(checkedId);
                mHorScrollView.smoothScrollTo(bt.getLeft(), 0);
            }
        });

        mVerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

    }

    private void changeOrigin(int orientation){
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            mHorScrollView.setVisibility(View.GONE);
            mVerScrollView.setVisibility(View.VISIBLE);
//            mHorScrollView.removeView(mRadioGroup);
//            mVerScrollView.addView(mRadioGroup);
//            mRadioGroup.setOrientation(RadioGroup.VERTICAL);
            ((RadioButton)mVerRadioGroup.findViewById(R.id.radio_5)).setChecked(true);
        }else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            mVerScrollView.setVisibility(View.GONE);
            mHorScrollView.setVisibility(View.VISIBLE);
//            mVerScrollView.removeView(mRadioGroup);
//            mHorScrollView.removeAllViews();
//            mHorScrollView.addView(mRadioGroup);
            mRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
            //((RadioButton)mRadioGroup.findViewById(R.id.radio_5)).setChecked(true);
            mRadioGroup.check(R.id.radio_5);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mLastOrientation != newConfig.orientation){
            changeOrigin(newConfig.orientation);
            mLastOrientation = newConfig.orientation;
        }
    }
}
