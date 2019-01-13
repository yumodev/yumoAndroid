package com.yumo.android.test.sys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yumo.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumo on 4/2/16.
 */
public class SharedCustomFragment extends Fragment {
    private final String TAG = "SharedCustomFragment";

    private ListView mList = null;
    private ActionSendAdapter mAdapter = null;
    List<Intent> mTargetedShareIntents = new ArrayList<Intent>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.action_send_list, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private boolean initView() {
        mList = (ListView) getView().findViewById(R.id.list);
        mAdapter = new ActionSendAdapter(getView().getContext().getApplicationContext());
        getApps();
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = mTargetedShareIntents.get(position);
                if (intent != null) {
                    startActivity(intent);
                }

            }
        });
        return true;
    }

    public void getApps() {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        List<ResolveInfo> resInfo = getContext().getPackageManager().queryIntentActivities(
                it, 0);
        mAdapter.setListData(resInfo);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("text/plain");

                targeted.putExtra(Intent.EXTRA_TEXT, "自定义分享");

                ActivityInfo activityInfo = info.activityInfo;

                targeted.setPackage(activityInfo.packageName);
                mTargetedShareIntents.add(targeted);
            }
        }
    }



    class ActionSendAdapter extends BaseAdapter {

        private Context mContext = null;
        private List<ResolveInfo> mListData = null;

        public List<ResolveInfo> getListData() {
            return mListData;
        }

        public void setListData(List<ResolveInfo> listData) {
            this.mListData = listData;
        }

        public ActionSendAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            if (mListData != null)
                return mListData.size();
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null
                    || !(convertView.getTag() instanceof ViewHolder)) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.action_send_list_item, null);
                holder = new ViewHolder();
                holder.mAppName = (TextView) convertView
                        .findViewById(R.id.name);
                holder.mAppIcon = (ImageView) convertView
                        .findViewById(R.id.img);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (mListData == null)
                return convertView;
            ResolveInfo resolveInfo = mListData.get(position);

            ActivityInfo activityInfo = resolveInfo.activityInfo;
            Log.d(TAG,"packageName:"+activityInfo.packageName+"  processName:"+activityInfo.processName+" ");
            holder.mAppName.setText(resolveInfo.loadLabel(mContext.getApplicationContext().getPackageManager()));

            //resolveInfo.
            holder.mAppIcon.setImageDrawable(resolveInfo.loadIcon(mContext.getApplicationContext().getPackageManager()));


            return convertView;
        }

        class ViewHolder {
            private TextView mAppName;
            private ImageView mAppIcon;
        }

    }
}
