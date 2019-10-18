package com.yumo.android.test.anim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yumo.android.R;
import com.yumo.android.test.anim.view.AutoPollRecyclerView;
import com.yumo.demo.view.YmTestFragment;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestAutoPollRecyclerView extends YmTestFragment {

  AutoPollRecyclerView mListView;
  @Override
  protected View getHeaderView() {
    mListView = new AutoPollRecyclerView(getActivity(), null);
    List<Integer> list = new LinkedList<>();
    list.add(R.drawable.magic_vpn_card_7);
    list.add(R.drawable.magic_vpn_card_30);
    AutoPollAdapter adapter = new AutoPollAdapter(getActivity(), list);
    mListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    //mListView.additionalStatetemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTALNTAL_LIST));
    mListView.setAdapter(adapter);
    if (true){ //保证itemCount的总个数宽度超过屏幕宽度->自己处理{
      mListView.start();
    }

    return mListView;
  }


  class AutoPollAdapter extends RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder> {
    private final Context mContext;
    private final List<Integer> mData;
    public AutoPollAdapter(Context context, List<Integer> list) {
      this.mContext = context;
      this.mData = list;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(mContext).inflate(R.layout.auto_scroll_card_item, parent, false);
      BaseViewHolder holder = new BaseViewHolder(view);
      return holder;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
      holder.imageView.setImageResource(mData.get(position % mData.size()));
    }
    @Override
    public int getItemCount() {
      return Integer.MAX_VALUE;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{
      ImageView imageView = null;
      public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_icon);
      }
    }
  }
}
