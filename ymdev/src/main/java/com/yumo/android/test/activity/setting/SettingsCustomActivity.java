package com.yumo.android.test.activity.setting;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.yumo.android.R;

import java.util.List;

/**
 * Created by trunx on 12/29/15.
 */
public class SettingsCustomActivity extends PreferenceActivity
        implements Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener,
        TestPreferenceFragment.FragmentListener {

    private final String LOG_TAG = "SettingCustomActivity";


    private Toolbar mToolbar;
    private String mTitle = "Settings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_page);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_id);
        mToolbar.setTitle("Custom UI PreferenceActivity");
        mToolbar.setNavigationIcon(R.drawable.head_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getFragmentManager().getBackStackEntryCount();
                Log.d(LOG_TAG, "onBackStackChanged"+count);
                if(count > 0){
                    getFragmentManager().popBackStack();
                }if (count == 0){
                    if (mToolbar.getTitle().equals(mTitle)){
                        finish();
                    }else{
                        getListView().setVisibility(View.VISIBLE);
                        mToolbar.setTitle(mTitle);
                    }

                }
            }
        });

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int count = getFragmentManager().getBackStackEntryCount();
                Log.d(LOG_TAG, "onBackStackChanged"+count);
                if (count <= 0){
                    getListView().setVisibility(View.VISIBLE);
                    mToolbar.setTitle(mTitle);
                }
            }
        });
    }



    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    public void onHeaderClick(Header header, int position) {
        Fragment f = Fragment.instantiate(this, header.fragment, null);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_container_id, f);
        transaction.addToBackStack(null);
        transaction.commit();

        mToolbar.setTitle(header.titleRes);

//        getFragmentManager().beginTransaction().replace(android.R.id.content,
//                new TestPreferenceFragment()).commit();

        getListView().setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    @Override
    public void setPageListener(PreferenceManager pm, PreferenceScreen ps) {
        Log.d(LOG_TAG, "setPageListener");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d(LOG_TAG, "onPreferenceChange");
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.d(LOG_TAG, "onPreferenceClick");
        return false;
    }

}
