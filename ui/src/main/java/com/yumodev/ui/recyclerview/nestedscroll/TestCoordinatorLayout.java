package com.yumodev.ui.recyclerview.nestedscroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumodev.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 17/12/19.
 */

public class TestCoordinatorLayout extends FrameLayout {

    private RecyclerView mListView = null;
    private TestCoordinatorLayout.ItemAdapter mAdapter = null;
    private List<String> mDataList = null;

    private LinearLayout mHeadView = null;

    private CoordinatorLayout mCoordinatorLayout = null;

    public TestCoordinatorLayout(@NonNull Context context) {
        super(context);
        setupUI();
    }

    public TestCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public TestCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }


    public void setupUI(){
        mCoordinatorLayout = (CoordinatorLayout) View.inflate(getContext(), R.layout.coordinatorlayout_test1, null);
        addView(mCoordinatorLayout);
        mHeadView = findViewById(R.id.header);
        mHeadView.setVisibility(View.GONE);
        initTestData();
        mListView = findViewById(R.id.list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TestCoordinatorLayout.ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        EditText mEditor = findViewById(R.id.editor);
        mEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initTestData(){
        if (mDataList == null){
            mDataList = new ArrayList<>();
        }

        for (int n =0; n< 50; n++){
            mDataList.add("test "+n);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<TestCoordinatorLayout.ItemAdapter.ItemViewHolder> {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public TestCoordinatorLayout.ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.recycler_simple_array_one, viewGroup, false);
            return new TestCoordinatorLayout.ItemAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final TestCoordinatorLayout.ItemAdapter.ItemViewHolder itemViewHolder, final int i) {
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
