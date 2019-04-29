package com.yumodev.ui.md;

import android.graphics.Color;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.ui.R;
import com.yumodev.ui.common.SnackbarUtils;

/**
 * Created by yumodev on 17/12/21.
 * SnackBar的使用测试
 */

public class SnackbarTestView extends YmTestFragment {

    public void testSnackbar(){
        Snackbar.make(getActivity().getWindow().getDecorView(), "test", Snackbar.LENGTH_SHORT)
                .setAction("test", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showToastMessage("测试点击事件");
                    }
                })
                .show();
    }

    /**
     * 修改SnackBar的背景颜色
     */
    public void testSnackbarBackGroud(){
        Snackbar snackbar = Snackbar.make(getView(), "test", Snackbar.LENGTH_SHORT)
                .setAction("test", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showToastMessage("测试点击事件");
                    }
                });

        setSnackbarColor(snackbar, Color.RED, Color.BLUE);
        snackbar.setActionTextColor(getResources().getColor(R.color.yellow));
        snackbar.show();
    }


    /**
     * 修改SnackBar的背景颜色
     */
    public void testSnackbarAction(){
        Snackbar snackbar = Snackbar.make(getView(), "test", Snackbar.LENGTH_SHORT)
                .setAction("test", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showToastMessage("测试点击事件");
                    }
                });

        snackbar.setActionTextColor(Color.WHITE);

        View view = snackbar.getView();
        if (view != null){
            Button snackbar_button = view.findViewById(R.id.snackbar_action);
            snackbar_button.setBackgroundColor(Color.BLUE);
        }
        snackbar.show();
    }

    /**
     * Add Snackbar View
     */
    public void testSnackbarAddView(){
        Snackbar snackbar = Snackbar.make(getView(), "test", Snackbar.LENGTH_SHORT)
                .setAction("test", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showToastMessage("测试点击事件");
                    }
                });

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        snackbarLayout.addView(imageView, 1, lp );
        //snackbar.setActionTextColor(getResources().getColor(R.color.yellow));

        snackbar.show();
    }

    /**
     * 修改显示位置
     */
    public void testSnackbarPostion(){
        Snackbar snackbar = Snackbar.make(getView(), "test", Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        int margin = 200;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.setMargins(200, 200, 200, 200);
        view.setLayoutParams(lp);

        //view.setPadding(200, 200, 200, 200);
        snackbar.show();
    }


    public void testSnackBarUtils(){
        SnackbarUtils.Long(getView(), "test")
                .margins(200).getSnackbar().show();


    }


    public void testSnackBarUtilsAddView(){
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        SnackbarUtils.Long(getView(), "test")
                .margins(200).addView(imageView, 0).getSnackbar().show();


    }


    public void testCustomLayout(){
        Snackbar snackbar = Snackbar.make(getView(), "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout)snackbar.getView();
        snackbarLayout.removeAllViews();
        ViewGroup rootView = (ViewGroup) View.inflate(snackbar.getContext(), R.layout.snackbar_layout, null);
        snackbarLayout.addView(rootView);
        TextView textView = rootView.findViewById(R.id.snackbar_action);
        textView.setText("test");

        snackbar.show();
    }


    private void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor){
        View view = snackbar.getView();
        if (view != null){
            view.setBackgroundColor(backgroundColor);
            TextView textView = view.findViewById(R.id.snackbar_action);
            textView.setTextColor(messageColor);
        }
    }
}
