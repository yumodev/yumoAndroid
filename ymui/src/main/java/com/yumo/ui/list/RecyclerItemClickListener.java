package com.yumo.ui.list;

import android.view.View;

/**
 * Created by yumodev on 17/12/9.
 */

public interface RecyclerItemClickListener {
    void onItemClick(final View view, final Object item, int position);
    void onItemLongClick(final View view, final Object item, int position);
}
