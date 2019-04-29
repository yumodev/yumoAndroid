package com.yumodev.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yumodev.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumo on 6/4/16.
 */
public class TestRecyclerViewOperation extends FrameLayout{
    private final String LOG_TAG = "TestRecyclerViewOpera";

    private RecyclerView mListView = null;
    private ItemAdapter mAdapter = null;
    private List<TestData> mDataList = null;

    public TestRecyclerViewOperation(Context context) {
        super(context);
    }

    public void setupUI(){
        initTestData();

        mListView = new RecyclerView(getContext());
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemAdapter(getContext());
        mListView.setAdapter(mAdapter);

        addView(mListView);
    }

    private void initTestData(){
        if (mDataList == null){
            mDataList = new ArrayList<TestData>();
        }

        for (int n =0; n< 10; n++){
            TestData data = new TestData();
            data.mText =  "test:"+n;
            mDataList.add(data);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(R.layout.recycler_operation_item, viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int pos) {
            final TestData data = mDataList.get(pos);
            itemViewHolder.mTextView.setText(data.mText);


            itemViewHolder.mDeleteBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = itemViewHolder.getAdapterPosition();
                    Log.d(LOG_TAG,"old:"+pos+" new:"+position);
                    mAdapter.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            });

            itemViewHolder.itemView.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), data.mText, Toast.LENGTH_SHORT).show();
                }
            });
        }



        @Override
        public int getItemCount() {
            if (mDataList == null){
                return 0;
            }

            return mDataList.size();
        }

        public int  getItemPostion(TestData data){
            return mDataList.indexOf(data);
        }

        public void remove(int position){
            mDataList.remove(position);
        }

        OnClickListener mClickListener = new OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.delete_id:{
                        Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            Button mDeleteBtn;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.drag_title_id);
                mDeleteBtn = (Button) itemView.findViewById(R.id.delete_id);
            }

        }
    }

    class TestData{
        String mText;
    }
}
