package com.yumodev.ui.module.stack.test;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.TextView;

import com.yumodev.ui.R;
import com.yumodev.ui.module.stack.StackChildView;
import com.yumodev.ui.module.stack.StackEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 18/1/11.
 */

public class StackViewPage extends FrameLayout {
    private StackView mStackView;
    private MyAdapter mAdapter;
    private List<StackEntry> mDataList = new ArrayList<>();
    public StackViewPage(@NonNull Context context) {
        super(context);
        initView();
    }

    public void initView(){
        View view = View.inflate(getContext(), R.layout.stack_page1, null);
        addView(view);
        createTestDatas();
        mStackView = findViewById(R.id.stack_view);
        mAdapter = new MyAdapter();
        mStackView.setAdapter(mAdapter);
        mStackView.advance();




        view.findViewById(R.id.add_btn).setOnClickListener(v -> {
            mDataList.add(StackEntry.newInstance("Stack "+mDataList.size()));
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

    class MyAdapter extends BaseAdapter{

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public StackEntry getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null){
//                convertView = new StackChildView(getContext(), getItem(position));
//            }
//            TextView titleView = convertView.findViewById(R.id.title_tv);
//            titleView.setText(getItem(position).title);
//            return convertView;

            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.baidu_logo);
            return imageView;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
