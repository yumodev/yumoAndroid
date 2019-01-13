package com.yumo.android.test.view.mdview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by wks on 1/17/16.
 */
public class ToolbarTestView extends YmTestFragment {

    public void testSimpleToolbar(){
        Toolbar toolbar = new Toolbar(getActivity().getApplicationContext());
        toolbar.setTitle("simpleTitle");
        toolbar.setSubtitle("simpleSubTitle");
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.head_back);
        toolbar.setLogo(R.drawable.ic_launcher);
        //toolbar.inflateMenu(R.menu.main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    showToastMessage(item.getTitle().toString());
                    return true;
                }
                return false;
            }
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        showToolbar(toolbar);
    }

    private void showToolbar(Toolbar toolbar){
        showTestView(toolbar, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
