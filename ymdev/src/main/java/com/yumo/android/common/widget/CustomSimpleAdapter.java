/**
 * @file

 * @author  yumo
 * @date    2015/01/26
 * @version 0.1
 */
/**

 */
package com.yumo.android.common.widget;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.yumo.android.common.inface.ListClickInterface;
import com.yumo.common.android.YmContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
/**
 * @class  FootPrintAdapter
 * @brief
 * @author yumo
 */
public class CustomSimpleAdapter extends SimpleAdapter {

    /**

     */
    private static final String TAG = CustomSimpleAdapter.class.getSimpleName();

    /**

     */
    private LayoutInflater mInflater = null;

    /**
     * @brief 显示相应列数据的控件
     */
    private int[] mToView = null;
    /**

     */
    private String[] mFromData = null;

    /**
     * @brief 数据list
     */
    private List<? extends Map<String, ?>> mData = null;

    /**
     * @brief layout资源id
     */
    private int mResource = -1;

    /**

     */
    private ListClickInterface mCallback = null;

    HashMap<Integer, ListClickInterface> mMapCallback = null;

    /**
     * @brief 控件绑定
     */
    private ViewBinder mViewBinder = null;

    /**

     */
    private Map<String, String> mItemHeight = new HashMap<String, String>();


    /**

     * @author    yumo

     * @param[in] data         列表数据


     * @param[in] toView          显示相应列数据的控件
     */
    public CustomSimpleAdapter(Context context,
                               List<? extends Map<String, ?>> data, int resource, String[] fromData,
                               int[] toView) {

        super(context, data, resource, fromData, toView);
        mData = data;
        mResource = resource;
        mFromData = fromData;
        mToView = toView;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = 0;
        if(mData != null){
            count = this.mData.size();
        }
        else{
            Log.d(TAG, "size:0");
        }

        return count;
    }

    /**

     * @author    yumo



     */
    public View getView(int position, View view, ViewGroup group) {
        Log.d(TAG, "getView() start");
        Log.d(TAG, "getView() end");
        return _createViewFromResource(position, view, group, mResource);
    }

    /**

     * @author    yumo

     */
    public void setCallback(ListClickInterface callback){
        Log.d(TAG, "setCallback() start.");
        mCallback = callback;
        Log.d(TAG, "setCallback() end.");
    }

    public void setMapCallback(HashMap<Integer, ListClickInterface> map)
    {
        mMapCallback = map;
    }

    /**

     * @author yumo




     */
    private View _createViewFromResource(final int position, View view, ViewGroup parent, int resource) {
        Log.d(TAG, "_createViewFromResource() start");
        View v = null;
        if (view == null) {
            v = mInflater.inflate(resource, parent, false);
            final int[] toView = mToView;
            final int count = toView.length;
            final View[] holder = new View[count];
            for (int i=0; i<count; i++) {
                holder[i] = v.findViewById(toView[i]);
                if(mMapCallback != null && mMapCallback.containsKey(toView[i]))
                {
                    final int resid = toView[i];
                    holder[i].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Log.d(TAG,"order is payorder:0");
                            mMapCallback.get(resid).onClick(position);
                        }
                    });
                }
            }
            v.setTag(holder);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(mCallback != null){
                        mCallback.onClick(position);
                    }
                }
            });
        }
        else {
            v = view;
        }
        _bindView(position, v);
        //Log.e(TAG, "adapter item heigh = " + v.getHeight());
        if(v.getHeight() > 0){
            mItemHeight.put(String.valueOf(position), String.valueOf(v.getHeight()));
        }
        else {
            //this.notifyDataSetChanged();
        }
        Log.d(TAG, "_createViewFromResource() end");
        return v;
    }

    public int getItemHeight(int position){
        int height = 0;
        String tmp = mItemHeight.get(String.valueOf(position));
        if(tmp != null && tmp.length() > 0){
            height = Integer.valueOf(mItemHeight.get(String.valueOf(position)).toString());
        }
        return height;
    }

    /**

     * @author yumo


     */
    private void _bindView(final int position, View view) {
        Log.d(TAG, "_bindView() start");
        final Map<String, ?> dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }
        final ViewBinder binder = mViewBinder;
        final View[] holder = (View[]) view.getTag();
        final String[] from = mFromData;
        final int[] to = mToView;
        final int count = to.length;
        for (int i=0; i<count; i++) {
            final View v = holder[i];
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }
                boolean bound = false;
                if (!bound) {
                    if (v instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);
                        }
                        else if(data instanceof String) {
                            Glide.with(YmContext.getInstance().getAppContext()).load((String)data).into((ImageView)v);
                        }
                        else {
                            Log.e(TAG, "_bindView(): unknow data instance");
                        }
                    }
                    else if (v instanceof TextView) {
                        setViewText((TextView) v, text);
                    }
                    else {
                        Log.e(TAG, "_bindView(): unknow v instance");
                    }
                }
            }
        }
        Log.d(TAG, "_bindView() end");
    }
    /**

     * @author yumo
     * @param  view 显示控件

     */
    public void setViewImage(ImageView view, Bitmap bitmap) {
        Log.d(TAG, "setViewImage() start");
        view.setImageBitmap(bitmap);
        Log.d(TAG, "setViewImage() end");
    }
    /**

     * @author    yumo
     * @param[in] view 显示控件

     */
    public void setViewImage(ImageView view, int value) {
        Log.d(TAG, "setViewImage() start");
        view.setImageResource(value);
        Log.d(TAG, "setViewImage() end");
    }

}