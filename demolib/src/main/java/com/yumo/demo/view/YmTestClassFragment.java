package com.yumo.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yumo.demo.R;
import com.yumo.demo.base.YmDemoBaseFragment;
import com.yumo.demo.config.Config;
import com.yumo.demo.entry.YmClass;
import com.yumo.demo.listener.UpdateTitleObservable;
import com.yumo.demo.utils.YmClassUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 15/11/20.
 */
public class YmTestClassFragment extends YmDemoBaseFragment {
    private RecyclerView mListView = null;
    private List<YmClass> mDataList = null;
    private String mPackageName = "";
    private int mToolbarVisible = View.VISIBLE;

    @Override
    protected View getContainerView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null){
            mPackageName = getArguments().getString("packageName");
        }

        mToolbar.setTitle(mPackageName);
        mToolbar.setNavigationIcon(R.drawable.head_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        UpdateTitleObservable.getInstance().setTitle(mPackageName);

        initData();

        CommonAdapter<YmClass> adapter = new CommonAdapter<YmClass>(getContext(), R.layout.linearlayout_text_item, mDataList) {
            @Override
            protected void convert(ViewHolder holder, final YmClass data, int position) {
                holder.setText(R.id.content, data.getDisplayName());
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                YmClass cls = mDataList.get(position);
                if (Activity.class.isAssignableFrom(cls.getCls())){
                    getActivity().startActivity(new Intent(getActivity(), cls.getCls()));
                }else{
                    YmTestFragment fragment = (YmTestFragment) Fragment.instantiate(getContext(), cls.getCls().getName());
                    Bundle bundle = new Bundle();
                    bundle.putInt(Config.ARGUMENT_TOOLBAR_VISIBLE, mToolbarVisible);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.test_fragment_id, fragment).commit();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mListView = new RecyclerView(getContext());
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setAdapter(adapter);
        return mListView;
    }

    private void getShowClassName(){

    }

    /**
     * yumodev
     * void
     * 2014-11-6
     */
    private void initData() {
        List<Class<?>> superClassList = new ArrayList<>();
        superClassList.add(YmTestFragment.class);
        superClassList.add(Activity.class);

        mDataList = YmClassUtils.getAllSubClassInPackage(getContext(), superClassList, mPackageName);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mToolbarVisible = bundle.getInt(Config.ARGUMENT_TOOLBAR_VISIBLE);
            if (mToolbar != null){
                mToolbar.setVisibility(mToolbarVisible);
            }
        }
    }

}
