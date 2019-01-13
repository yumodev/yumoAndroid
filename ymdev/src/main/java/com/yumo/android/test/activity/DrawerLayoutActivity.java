package com.yumo.android.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.yumo.android.R;
import com.yumo.android.test.activity.fragment.FragmentDemo;

/**
 * Created by yumodev on 17/11/7.
 */

public class DrawerLayoutActivity extends FragmentActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout_page);
        setupUI();
    }

    private void setupUI(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void onLeft(View view){
        openDrawer();
    }

    /**
     * 打开侧边栏
     */
    private void openDrawer(){
        if (mDrawerLayout != null){
            if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                mDrawerLayout.openDrawer(Gravity.START);
            }
        }
    }

    /**
     * 关闭侧边栏
     */
    private void closeDrawer(){
        if (mDrawerLayout != null){
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                mDrawerLayout.closeDrawers();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            showFragment();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(){
        FragmentDemo fragment = new FragmentDemo();
        //getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.test_fragment_id, fragment).commit();

    }
}
