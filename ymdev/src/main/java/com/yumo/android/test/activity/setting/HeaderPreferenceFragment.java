package com.yumo.android.test.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.yumo.android.R;

/**
 * Created by wks on 1/12/16.
 */
public class HeaderPreferenceFragment extends YmPreferenceFragment {

    private HeaderFragmentListener mFragmentListener;

    public interface HeaderFragmentListener {
        void setHeaderListener(PreferenceManager pm, PreferenceScreen ps);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_headers_screen);
        PreferenceManager.setDefaultValues(getActivity(),
                R.xml.pref_headers_screen, false);

        mFragmentListener.setHeaderListener(getPreferenceManager(), getPreferenceScreen());
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentListener = (HeaderFragmentListener) activity;
    }
}
