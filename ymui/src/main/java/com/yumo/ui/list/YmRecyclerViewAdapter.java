package com.yumo.ui.list;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yumodev on 9/10/16.
 */
public abstract class YmRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected RecyclerItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(RecyclerItemClickListener listener){
        mOnItemClickListener = listener;
    }
}
