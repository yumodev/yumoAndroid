package com.yumo.android.test.activity.setting;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.yumo.android.R;

/**
 * Created by wks on 12/28/15.
 */
public class SettingAppCompatActivity extends AppCompatActivity
        implements IPreferenceActivity,
        TestPreferenceFragment.FragmentListener,
        HeaderPreferenceFragment.HeaderFragmentListener{

    private final String LOG_TAG = "SettingAppCompat";
    private Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_page);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_id);
        mToolbar.setTitle("Setting");
        mToolbar.setNavigationIcon(R.drawable.toolbar_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //setSupportActionBar(mToolbar);
        switchFragment(new HeaderPreferenceFragment(), "setting");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void subPageBack() {

    }

    private void switchFragment(Fragment fragment, String title){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_container_id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        mToolbar.setTitle(title);
    }



    @Override
    public void setPageListener(PreferenceManager pm, PreferenceScreen ps) {
        Log.d(LOG_TAG, "setPageListener");
    }

    @Override
    public void setHeaderListener(PreferenceManager pm, PreferenceScreen ps) {
        Log.d(LOG_TAG, "setHeaderListener");

        pm.findPreference(getString(R.string.setting_key_header_one)).setOnPreferenceClickListener(this);
        pm.findPreference(getString(R.string.setting_key_header_two)).setOnPreferenceClickListener(this);
        pm.findPreference(getString(R.string.setting_key_header_three)).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d(LOG_TAG, "onPreferenceChange");
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.d(LOG_TAG, "onPreferenceClick:"+preference.getKey());
        onHeaderClick(preference);

        return false;
    }

    public void onHeaderClick(Preference preference) {
        Fragment fragment = Fragment.instantiate(this, preference.getFragment(), null);
        switchFragment(fragment, preference.getTitle().toString());
    }

}
