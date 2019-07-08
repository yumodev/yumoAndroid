package com.yumo.android;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yumo.common.android.PermissionUtil;
import com.yumo.common.android.YmAppUtil;
import com.yumo.common.log.Log;
import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.config.Config;
import com.yumo.demo.listener.UpdateTitleObservable;
import com.yumo.demo.view.YmTestPackageFragment;

import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final String LOG_TAG = Log.LIB_TAG;

    DrawerLayout mDrawerLayout = null;
    protected Toolbar mToolbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        YmAppUtil.hookWebView();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            WebView.enableSlowWholeDocumentDraw();
        }

        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "current activity:"+getClassLoader().toString());
        Log.i(LOG_TAG, "activity:"+ Activity.class.getClassLoader().toString());
        Log.i(LOG_TAG, "Context:"+ Context.class.getClassLoader().toString());
        setupUI();

        //不Register的话，图片请求不会经过OkHttpClient
        Glide.get(MainActivity.this)
                .register(          //使用okhttp作为图片请求
                        GlideUrl.class
                        ,InputStream.class
                        ,new OkHttpUrlLoader.Factory(YmOkHttpUtil.getOkHttpClient()));

        PermissionUtil.requestPermission(this);
    }

    private void setupUI(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.i(LOG_TAG, "onDrawerSlide:"+slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
               Log.i(LOG_TAG, "打开了侧边栏");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.i(LOG_TAG, "关闭了侧边栏");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.i(LOG_TAG, "onDrawerStateChanged:"+newState);
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:{
                        ((ViewGroup)findViewById(R.id.test_fragment_id)).removeAllViews();
                        break;
                    }
                    case R.id.navigation_left:{
                        showTestPackageHomePage();
                        break;
                    }
                    case R.id.navigation_right:{
                        ((ViewGroup)findViewById(R.id.test_fragment_id)).removeAllViews();
                        break;
                    }
                }
                return false;
            }
        });
        //showTestPackageHomePage();
        UpdateTitleObservable.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                mToolbar.setTitle(String.valueOf(arg));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null){
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                mDrawerLayout.closeDrawers();
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
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
    private void showTestPackageHomePage(){
        YmTestPackageFragment fragment = new YmTestPackageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ARGUMENT_TOOLBAR_VISIBLE, View.GONE);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.test_fragment_id, fragment, "package").commit();
    }


}
