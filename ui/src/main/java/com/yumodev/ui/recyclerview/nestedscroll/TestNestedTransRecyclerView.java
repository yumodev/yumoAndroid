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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.Define;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wks on 15/11/22.
 */
public class TestNestedTransRecyclerView extends FrameLayout {
    final String LOG_TAG = Define.INSTANCE.getLOG_TAG();

    private RecyclerView mListView = null;
    private ItemAdapter mAdapter = null;
    private List<String> mDataList = null;

    private LinearLayout mHeadView = null;

    private NestedTransLayout mNestedLayout;

    public TestNestedTransRecyclerView(Context context) {
        super(context);
        setupUI();
    }

    public TestNestedTransRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();

    }

    public TestNestedTransRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }


    public void setupUI(){
        mNestedLayout = new NestedTransLayout(getContext());
        mNestedLayout.setBackgroundResource(R.color.blue);
        //mNestedLayout.setOrientation(LinearLayout.VERTICAL);
        addView(mNestedLayout);
        View view = View.inflate(getContext(), R.layout.nested_trans_layout, null);
        mNestedLayout.addView(view);
        mHeadView = findViewById(R.id.header);
        mHeadView.setVisibility(View.GONE);
        initTestData();
        mListView = findViewById(R.id.list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);

        mListView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return false;
            }
        });

        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i(LOG_TAG, newState+"");
                if (newState == 0){
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter.notifyDataSetChanged();

        mNestedLayout.setChildView((ViewGroup) view);
        mNestedLayout.setStickView(mHeadView, getResources().getDimensionPixelSize(R.dimen.dimen_40));
        mNestedLayout.setChildScrollView(mListView);

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

        mEditor.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                mNestedLayout.setCanStick(true);
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
