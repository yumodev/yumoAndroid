
package com.yumo.android.test.view.menu;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.android.R;

/**
 * MenuDemoActivity.java
 * yumodev 测试菜单
 * 2014-12-3
 */
public class MenuDemoActivity extends Activity implements View.OnClickListener {
    private final String TAG = "MenuDemoActivity";

    private Button mContextMenu = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_demo_page);
        initView();
    }

    /**
     * 初始化界面
     * yumodev
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("Menu");

        //createContextMenu();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        this.getMenuInflater().inflate(R.menu.options_menu_demo, menu);

        //动态添加
        menu.add(100, 100, 1, "menu_one");
        menu.add(100, 102, 1, "menu_two");
        menu.add(100, 103, 1, "menu_Three");
        menu.add(100, 104, 1, "menu_Four");

        SubMenu subMenu = menu.addSubMenu("menu_sub_one");
        subMenu.add(1, 1, 1, "one");
        subMenu.add(1, 2, 1, "one");
        subMenu.add(1, 3, 1, "one");
        subMenu.add(1, 4, 1, "one");
        subMenu.setHeaderIcon(R.drawable.ic_launcher);
        subMenu.setHeaderTitle("menu_sub_one");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Toast.makeText(this, "click menu setting", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                item.setIntent(intent);
                break;
            }
            case R.id.action_menu_one: {
                Toast.makeText(this, "click menu one", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_menu_two: {
                Toast.makeText(this, "click menu two", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_menu_three: {
                Toast.makeText(this, "click menu three", Toast.LENGTH_SHORT).show();
                break;
            }
            default: {

                if (item.getGroupId() == 1) {
                    Toast.makeText(this, "click sub menu " + item.getTitle(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "click menu " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        Log.d(TAG, "onOptionsMenuClosed");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onCreateContextMenu:" + v.getId());
        if (v.getId() == R.id.contextmenu) {
            menu.setHeaderIcon(R.drawable.ic_launcher);
            menu.setHeaderTitle("contextView");
            menu.add(1, 1, 1, "menu one");
            menu.add(1, 2, 1, "menu two");
            menu.add(1, 3, 1, "menu three");
            menu.add(1, 4, 1, "menu four");
        }

        if (v.getId() == R.id.contextmenu_one) {
            menu.setHeaderIcon(R.drawable.ic_launcher);
            menu.setHeaderTitle("contextView");
            this.getMenuInflater().inflate(R.menu.options_menu_demo, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected");
        Toast.makeText(this, item.getItemId() + " " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;

    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
        Log.d(TAG, "onContextMenuClosed");
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, TAG + "onclick backimg");
                finish();
                break;
            }
            case R.id.contextmenu: {
                createContextMenu();
            }

            case R.id.contextmenu_one: {
                Button btnOne = (Button) findViewById(R.id.contextmenu_one);
                registerForContextMenu(btnOne);
            }
        }
    }

    private void createContextMenu() {
        Log.d(TAG, "createContextMenu");
        mContextMenu = (Button) findViewById(R.id.contextmenu);
        registerForContextMenu(mContextMenu);
    }
}

