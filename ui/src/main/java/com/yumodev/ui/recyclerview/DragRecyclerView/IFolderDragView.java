package com.yumodev.ui.recyclerview.DragRecyclerView;

import android.view.View;

import java.util.List;

/**
 * Created by yumodev on 17/4/24.
 * 文件夹操作的类
 */

public interface IFolderDragView {
    boolean openFolder(List<DragItemBean> dataList);
    boolean closeFolder();
    boolean isShowing();
    View getView();
}
