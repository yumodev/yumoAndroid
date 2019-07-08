package com.yumo.android.test.view.marquee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TestMarqueeView extends YmTestFragment {

    private View mHeaderView = null;
    @Override
    protected View getHeaderView() {
        if (mHeaderView == null){
            mHeaderView = createMarqueeView();
        }
        return mHeaderView;
    }

    private View createMarqueeView(){
        LinearLayout linearLayout = (LinearLayout) View.inflate(getContext(), R.layout.marquee, null);
//        MarqueeTextView marqueeTextView = linearLayout.findViewById(R.id.marquee_view);
//        marqueeTextView.setText("ddkkdkdkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk打开坎坎坷坷坎坎坷坷打开坎坎坷坷坎坎坷坷看看");
//        marqueeTextView.setSingleLine(true);
        //marqueeTextView.startScroll();


        MarqueeTextView2 marqueeTextView2 = linearLayout.findViewById(R.id.marquee_view2);
        marqueeTextView2.setText("ddkkdkdkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk打开坎坎坷坷坎坎坷坷打开坎坎坷坷坎坎坷坷看看");
        marqueeTextView2.setSingleLine(true);
        marqueeTextView2.startScroll();

        MarqueeTextView1 textView = linearLayout.findViewById(R.id.marquee_end);
        textView.setText("ddkkdkdkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk打开坎坎坷坷坎坎坷坷打开坎坎坷坷坎坎坷坷看看");
        textView.setSelected(true);
        textView.requestFocus();
        return linearLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        MarqueeTextView1 textView = getHeaderView().findViewById(R.id.marquee_end);
        textView.setText("ddkkdkdkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk打开坎坎坷坷坎坎坷坷打开坎坎坷坷坎坎坷坷看看");
        textView.setSelected(true);
        textView.requestFocus();
    }
}
