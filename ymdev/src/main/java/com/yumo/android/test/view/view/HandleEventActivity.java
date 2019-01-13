/**
 * HandleEventActivity.java
 * yumo
 * 2014-12-2
 * TODO
 */
package com.yumo.android.test.view.view;

import java.util.ArrayList;
import java.util.List;

import com.yumo.android.R;
import com.yumo.common.android.YmTouchEventUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * yumo
 * 研究事件分发和 事件处理机制。
 */
public class HandleEventActivity extends Activity implements View.OnClickListener {
    private final String TAG = "HandleEventActivity";

    private ListView mList = null;
    private EventAdapter mAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.handle_event_page);
        initView();
    }

    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("Handle_Event");
        titleText.setOnClickListener(this);
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new EventAdapter(this);
        mList.setAdapter(mAdapter);
        initData();
        return true;
    }

    private boolean initData() {
        for (int n = 0; n < 20; n++) {
            Data data = new Data();
            mAdapter.addData(data);
        }
        mAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w(TAG,"dispatchTouchEvent --> " + YmTouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
        //return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        Log.w(TAG, "onTouchEvent --> " + YmTouchEventUtil.getTouchAction(event.getAction()));

        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, "onclick backimg");
                finish();
                break;
            }
            case R.id.title: {
                Log.d(TAG, "title onClick");
                break;
            }
        }
    }


    class EventAdapter extends BaseAdapter {
        private final String TAG_ADP = "EventAdapter";
        private List<Data> mList = null;
        private Context mContext = null;
        private LayoutInflater mLayoutInflater = null;

        public EventAdapter(Context context) {
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            if (mList != null) return mList.size();
            return 0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            if (mList != null) return mList.get(position);
            return null;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.list_item_demo_one, parent, false);
                holder = new Holder();
                holder.mButton = (Button) convertView.findViewById(R.id.btn);
                holder.mTextView = (TextView) convertView.findViewById(R.id.text_view);
                holder.mEditView = (EditText) convertView.findViewById(R.id.edit_text);
                holder.mNumText = (TextView) convertView.findViewById(R.id.text_num);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            //Log.d(TAG, TAG+"getView :position:"+position + " count:"+getCount());

            Data data = mList.get(position);
            holder.mTextView.setText(data.mText);
            holder.mNumText.setText((position + 1) + "、");
            holder.mButton.setText(data.mButton);
            return convertView;
        }

        public boolean addDatas(ArrayList<Data> poiInfos) {
            if (mList == null) mList = new ArrayList<Data>();
            mList = poiInfos;
            return true;
        }

        public boolean addData(Data data) {
            if (mList == null) mList = new ArrayList<Data>();
            mList.add(data);
            return true;
        }

        public void clear() {
            mList.clear();
        }
    }

    class Data {
        int mNum = 0;
        String mButton = "Button";
        String mText = "Text";
        String mEdit = "Edit";
    }

    class Holder {
        TextView mNumText = null;
        TextView mTextView = null;
        EditText mEditView = null;
        Button mButton = null;
    }
}

