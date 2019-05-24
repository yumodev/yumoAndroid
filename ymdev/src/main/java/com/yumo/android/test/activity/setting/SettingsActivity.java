package com.yumo.android.test.activity.setting;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.yumo.android.R;

/**
 * Created by yumo on 15/11/22.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener{

    private final String LOG_TAG = "SettingsActivity";

    private Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_page);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_id);
        mToolbar.setTitle("PreferenceActivity");
        mToolbar.setNavigationIcon(R.drawable.toolbar_back);

        addPreferencesFromResource(R.xml.pref_headers_screen);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG, intent.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Preference preference = getPreferenceManager().findPreference(getString(R.string.setting_key_header_one));
        preference.setOnPreferenceClickListener(this);
        Log.d(LOG_TAG, "onResume:"+preference.getKey());
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.d(LOG_TAG, "onPreferenceClick:"+preference.getKey() + "fragment:"+preference.getFragment());
        onHeaderClick(preference);
        return false;
    }

    public void onHeaderClick(Preference preference) {
        Fragment f = Fragment.instantiate(this, preference.getFragment(), null);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_container_id, f);
        transaction.addToBackStack(null);
        transaction.commit();

        mToolbar.setTitle(preference.getTitle());

        getListView().setVisibility(View.GONE);
    }
}
