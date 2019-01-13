package com.yumo.android.test.view.view;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 6/4/16.
 * TextView 测试类
 */
public class TextViewTestView extends YmTestFragment {

    public void testShowTextView(){
        TextView tv = new TextView(getContext());
        tv.setText("textView");
        showTestView(tv);
    }

    /**
     * textView中显示图片
     */
    public void testShowImgTextView(){
        TextView tv = new TextView(getContext());
        tv.setTextColor(getContext().getResources().getColor(R.color.common_text_color));
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.back_p);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        //tv.setCompoundDrawablesWithIntrinsicBounds(null,null
        //        , getResources().getDrawable(R.drawable.ic_launcher), null);
        tv.setText("textView家得静静地靠进口的健康健康的健康的健康健康的健康的健康的健康的健康的健康的健康的健康的健康的健康");
        //tv.setPadding(3000,0,0,0);
        showTextView(tv);
    }

    private void showTextView(TextView tv){
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setBackgroundResource(R.color.common_page_bg);
        rootView.addView(tv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        showTestView(rootView);
    }

    public void testShowImg(){
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(getContext()).inflate(
                R.layout.textview_img, null);
        showTestView(rootView);
    }
}
