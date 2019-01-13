package com.yumo.android.test.activity.setting;

import android.preference.PreferenceFragment;

/**
 * Created by wks on 1/25/16.
 */
public class YmPreferenceFragment extends PreferenceFragment implements IFragmentBackPress{
    @Override
    public boolean handleBackPress() {
        return false;
    }
}
