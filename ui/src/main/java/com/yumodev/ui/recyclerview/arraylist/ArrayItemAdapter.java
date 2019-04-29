package com.yumodev.ui.recyclerview.arraylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumodev.ui.R;

import java.util.List;
import java.util.Map;

/**
 * Created by yumodev on 15/12/4.
 */
public class ArrayItemAdapter  extends RecyclerView.Adapter<ArrayItemAdapter.ItemViewHolder> {

    private LayoutInflater mInflater;

    private int mLayoutId;

    private List<? extends Map<String, ?>> mData;
    private int[] mTo;
    private String[] mFrom;

    public ArrayItemAdapter(Context context,  List<? extends Map<String, ?>> data, int layoutRes, String[] from, int[] to) {
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutRes;
        mFrom = from;
        mTo = to;

        mData = data;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(mLayoutId, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
        Map<String, ?> data = mData.get(i);

        for (String from : mFrom){
            String textValue = data.get(from).toString();
            itemViewHolder.mTextView.setText(textValue);
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }

        return mData.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        CheckBox mCheckBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.content);
        }

    }
}
