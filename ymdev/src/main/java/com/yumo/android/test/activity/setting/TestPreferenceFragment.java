package com.yumo.android.test.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.yumo.android.R;

/**
 * Created by trunx on 12/28/15.
 */
public class TestPreferenceFragment extends PreferenceFragment {

    private FragmentListener mFragmentListener;

    public interface FragmentListener {
        public void setPageListener(PreferenceManager pm, PreferenceScreen ps);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.checkbox);
        PreferenceManager.setDefaultValues(getActivity(),
                R.xml.checkbox, false);

        mFragmentListener.setPageListener(getPreferenceManager(), getPreferenceScreen());
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentListener = (FragmentListener) activity;
    }
}
