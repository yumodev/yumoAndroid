package com.yumodev.ui.recyclerview.nestedscroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumodev.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wks on 15/11/22.
 */
public class TestNestedScrollViewRecyclerView extends FrameLayout {

    private RecyclerView mListView = null;
    private ItemAdapter mAdapter = null;
    private List<String> mDataList = null;

    private LinearLayout mHeadView = null;

    private NestedLayout mNestedLayout;

    public TestNestedScrollViewRecyclerView(Context context) {
        super(context);
    }

    public TestNestedScrollViewRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public TestNestedScrollViewRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setupUI(){
        mHeadView = findViewById(R.id.header);
        initTestData();
        mListView = findViewById(R.id.list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initTestData(){
        if (mDataList == null){
            mDataList = new ArrayList<>();
        }

        for (int n =0; n< 50; n++){
            mDataList.add("test "+n);
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
            if (mDataList == null){
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
