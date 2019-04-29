package com.yumodev.ui.module.stack.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yumodev.ui.R;
import com.yumodev.ui.module.stack.StackEntry;
import com.yumodev.ui.module.stack.StackView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 18/1/10.
 */

public class StackPage extends FrameLayout {

    private List<StackEntry> mDataList = new ArrayList<>();
    private StackView mStackView;

    public StackPage(@NonNull Context context) {
        super(context);
        initView();
    }

    public StackPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StackPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        View view = View.inflate(getContext(), R.layout.stack_page, null);
        addView(view);
        mStackView = findViewById(R.id.stack_view);
        createTestDatas();
        mStackView.setDataList(mDataList);


        view.findViewById(R.id.add_btn).setOnClickListener(v -> {
            mDataList.add(StackEntry.newInstance("Stack "+mDataList.size()));
            mStackView.setDataList(mDataList);
        });

        view.findViewById(R.id.close_all_btn).setOnClickListener(v -> {
             mDataList.clear();
        });

    }

    private void createTestDatas(){
        for (int i = 0; i < 10; i++){
            mDataList.add(StackEntry.newInstance("Stack "+i));
        }
    }
}
