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

package com.yumo.android.test.view.recyclerview;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yumo.android.test.view.recyclerview.animator.FadeInLeftAnimator;
import com.yumo.android.test.view.recyclerview.touchhelper.OnStartDragListener;
import com.yumo.android.test.view.recyclerview.touchhelper.RecyclerListAdapter;


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
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAdapter.add("add item", 2);
            }
        });

        Button delBtn = new Button(getContext());
        delBtn.setText("del");
        toolbar.addView(delBtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.del(2);
            }
        });

        mAdapter = new RecyclerListAdapter(getContext(), null);

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new FadeInLeftAnimator());

//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(recyclerView);

        root.addView(recyclerView);

        addView(root);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
