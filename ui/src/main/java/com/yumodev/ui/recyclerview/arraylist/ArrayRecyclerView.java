package com.yumodev.ui.recyclerview.arraylist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;


import com.yumodev.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wks on 15/11/22.
 */
public class ArrayRecyclerView extends FrameLayout {

    private RecyclerView mListView = null;
    private ArrayItemAdapter mAdapter = null;
    private List<? extends Map<String, ?>> mDataList = null;

    public ArrayRecyclerView(Context context) {
        super(context);
    }

    public void setupUI() {
        mListView = new RecyclerView(getContext());
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArrayItemAdapter(getContext(),
                getData(),
                R.layout.linear_list_item,
                new String[]{"name"},
                new int[]{R.id.content});
        mListView.setAdapter(mAdapter);

        addView(mListView);
    }

    private List<? extends Map<String, String>> getData() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int n = 0; n < 50; n++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", "text data:" + n);
            list.add(map);
        }

        return list;
    }


}
