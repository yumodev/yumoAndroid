package com.yumo.demo.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yumo.demo.R;
import com.yumo.demo.base.YmDemoBaseFragment;
import com.yumo.demo.config.Config;
import com.yumo.demo.entry.YmPackageInfo;
import com.yumo.demo.listener.IGetPackageData;
import com.yumo.demo.utils.YmClassUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 15/11/20.
 * 显示包名目录
 */
public class YmTestPackageFragment extends YmDemoBaseFragment {
    private RecyclerView mListView = null;
    private List<YmPackageInfo> mDataList = new ArrayList<>();
    private int mToolbarVisible = View.VISIBLE;

    @Override
    protected View getContainerView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("DemoTest");
        mToolbar.setNavigationIcon(R.drawable.head_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initData();

        CommonAdapter<YmPackageInfo> adapter = new CommonAdapter<YmPackageInfo>(getContext(), R.layout.linearlayout_text_item, mDataList) {
            @Override
            protected void convert(ViewHolder holder, YmPackageInfo testPackageInfo, int position) {
                holder.setText(R.id.content, testPackageInfo.mTitle);
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                YmPackageInfo data = mDataList.get(position);
                String packageName = data.mPackageName;
                Bundle bundle = new Bundle();
                bundle.putString("packageName", packageName);
                bundle.putInt(Config.ARGUMENT_TOOLBAR_VISIBLE, mToolbarVisible);

                YmTestClassFragment classFragment = new YmTestClassFragment();
                classFragment.setArguments(bundle);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.test_fragment_id, classFragment).commit();
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

    /**
     * 初始化包名
     * yumodev
     * void
     */
    private void initData(){
        String subClassName = YmClassUtils.getConfigPackageData(getContext(), IGetPackageData.class);
        if (TextUtils.isEmpty(subClassName)){
            return;
        }

        try{
            Class<?> cls = Class.forName(subClassName);
            Method getPackageList = cls.getMethod("getPackageList", new Class<?>[] {});
            mDataList = (List<YmPackageInfo>)getPackageList.invoke(cls.newInstance());
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
