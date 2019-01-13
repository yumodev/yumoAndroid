package com.yumo.android.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yumo.android.R;

/**
 * Created by yumodev on 17/11/7.
 */

public class DrawerlayoutFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new DrawerlayoutFragment()).commit();
    }

    public static class DrawerlayoutFragment extends Fragment{
        DrawerLayout mDrawerLayout = null;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //return super.onCreateView(inflater, container, savedInstanceState);
            View rootView = inflater.inflate(R.layout.drawerlayout_page, null, false);
            setupUI(rootView);
            return rootView;
        }

        private void setupUI(View view){
            mDrawerLayout = view.findViewById(R.id.drawer_layout);
            view.findViewById(R.id.open_left_drawer_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDrawer();
                }
            });
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
    }
}
