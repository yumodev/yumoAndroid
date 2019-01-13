package com.yumo.android.test.activity.setting;

import android.preference.Preference;

/**
 * Created by trunx on 1/25/16.
 */
public interface IPreferenceActivity extends
        Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener
{
    @Override
    boolean onPreferenceChange(Preference preference, Object newValue);

    @Override
    boolean onPreferenceClick(Preference preference);

    void subPageBack();
}
