/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yumodev.ui.recyclerview.touchlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yumodev.ui.recyclerview.animator.FadeInLeftAnimator;
import com.yumodev.ui.recyclerview.touchlist.OnStartDragListener;
import com.yumodev.ui.recyclerview.touchlist.RecyclerListAdapter;


/**
 * @author Paul Burke (ipaulpro)
 */
public class ListAnimatorView extends FrameLayout implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    RecyclerListAdapter mAdapter = null;
    public ListAnimatorView(Context context) {
        super(context);
    }

    public void setupUI(){
        LinearLayout root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.VERTICAL);

        LinearLayout toolbar = new LinearLayout(getContext());
        toolbar.setOrientation(LinearLayout.HORIZONTAL);
        root.addView(toolbar);

        Button addBtn = new Button(getContext());
        addBtn.setText("add");
        toolbar.addView(addBtn);
        addBtn.setOnClickListener(v -> mAdapter.add("add item", 2));

        Button delBtn = new Button(getContext());
        delBtn.setText("del");
        toolbar.addView(delBtn);
        delBtn.setOnClickListener(v -> mAdapter.del(2));

        mAdapter = new RecyclerListAdapter(getContext(), this);

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        root.addView(recyclerView);

        addView(root);

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        //mItemTouchHelper.startDrag(viewHolder);
    }
}
