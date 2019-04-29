package com.yumodev.ui.recyclerview.test;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.yumodev.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 15/11/22.
 * 一个普通的GrideRecyclerView Demo
 */
public class GridRecyclerView extends FrameLayout {

    private RecyclerView mListView = null;
    private ItemAdapter mAdapter = null;
    private List<String> mDataList = null;

    public GridRecyclerView(Context context) {
        super(context);
    }

    public void setupUI() {
        initTestData();

        mListView = new RecyclerView(getContext());
        mListView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);

        addView(mListView);
    }

    private void initTestData() {
        if (mDataList == null) {
            mDataList = new ArrayList<String>();
        }

        for (int n = 0; n < 50; n++) {
            mDataList.add("test " + n);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.recycler_simple_array_one, viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
            itemViewHolder.mTextView.setText(mDataList.get(i));
        }

        @Override
        public int getItemCount() {
            if (mDataList == null) {
                return 0;
            }

            return mDataList.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.title);
            }
        }
    }

}
