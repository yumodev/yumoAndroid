package com.yumo.demo.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yumodev on 17/2/15.
 */

public interface RecyclerViewItemClickListener {

    void onItemClick(RecyclerView.Adapter adapter, View v, int position);
}
