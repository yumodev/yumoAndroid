package com.yumo.android.test.media.custom;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yumo.android.R;

/**
 * Created by mac on 15/7/20.
 */
public class RoundFragment extends Fragment {
    private final String TAG = "RoundFragment ";

    private Spinner mSpinner = null;
    private ImageView mImageView = null;

    private final String LIST_ROUND_XML = "round_xml";
    private final String LIST_ROUND_XML_TOP = "round_xml_top";
    private final String LIST_ROUND_SHAPE_DRAWABLE = "round_shape_drawable";

    private String[] mLists = {LIST_ROUND_XML, LIST_ROUND_XML_TOP, LIST_ROUND_SHAPE_DRAWABLE};

    private ViewGroup mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = (ViewGroup) inflater.inflate(R.layout.round_demo_page, null);
        }
        initView();
        return null;
    }

    /**
     * TODO 初始化界面
     * yumo
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        ImageView backImg = (ImageView) mRootView.findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);

        TextView titleText = (TextView) mRootView.findViewById(R.id.title);
        titleText.setText("RoundBitmap");

        mImageView = (ImageView) mRootView.findViewById(R.id.img);


        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mLists);
        mSpinner = (Spinner) mRootView.findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                mImageView.setBackgroundDrawable(null);
                if (mLists[position] == LIST_ROUND_XML) {
                    mImageView.setImageResource(R.drawable.corners_bg);
                } else if (mLists[position] == LIST_ROUND_XML_TOP) {
                    mImageView.setImageResource(R.drawable.corners_top_bg);
                } else if (mLists[position] == LIST_ROUND_SHAPE_DRAWABLE) {
                    mImageView.setImageDrawable(null);
                    mImageView.setBackgroundDrawable(getRoundShapeDrawable());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinner.setSelection(0);
        return true;
    }


    private Drawable getRoundShapeDrawable() {
        float radius = 20.0f;
        int bgColor = Color.parseColor("#ff000000");
        float[] outerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(bgColor);
        drawable.getPaint().setStyle(Paint.Style.FILL);

        return drawable;
    }
}
