/**
 * Created by yumodev on 5/13/16.
 * 一个检测软键盘弹出的基类。如果想让该类发生作用，必须设置为根布局。
 */
package com.yumodev.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.yumodev.ui.util.YmViewUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SoftInputView extends FrameLayout{
    private final String LOG_TAG = "SoftInputView";

    private int mMagicNumber = 300;
    private boolean mIsShow = false;
    private boolean mShouldNotify = false;
    private int mSoftInputHeight = 0;

    private List<WeakReference<SoftInputListener>> mListenerList = new ArrayList<>();

    public SoftInputView(Context context) {
        super(context);
        initMagicNumber();
    }

    public SoftInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMagicNumber();
    }

    public SoftInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMagicNumber();
    }

    private void initMagicNumber(){
        float scale = YmViewUtils.getDensity(getContext());
        if (scale > 2){
            mMagicNumber = 300;
        }else{
            mMagicNumber = 200;
        }
        Log.i(LOG_TAG, "magicNumber:"+ mMagicNumber + " scale:" + scale);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int screenHeight = YmViewUtils.getScreenHeight(getContext()) - YmViewUtils.getStatusBarHeight(getContext());
        int screenWidth = YmViewUtils.getScreenWidth(getContext());

        Log.d(LOG_TAG, "width:"+getWidth() + "height:"+getHeight() + "mScreenHeight:" + screenHeight + " mScreenWidth:"+screenWidth);
        Log.d(LOG_TAG, "onSizeChanged: w:" + w + " h:" + h + " oldW:" + oldw + "oldH:" + oldh);

        if (w == oldw && oldh != h){
            if (screenHeight - h > mMagicNumber){
                mIsShow = true;
                mSoftInputHeight = screenHeight - h;
            }else{
                mIsShow = false;
            }

            mShouldNotify = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(LOG_TAG, "onLayout: changed:" + changed + "l:" + l + " t:" + t + " r:" + r + " b:" + b );
        if (mShouldNotify && changed){
            Log.d(LOG_TAG, "onLayout: softInputStatus:" + mIsShow);
            notifySoftInoutChangeListener(mIsShow, mSoftInputHeight);
            mShouldNotify = false;
        }
    }

    public void addSoftInputListener(SoftInputListener listener){
        mListenerList.add(new WeakReference<SoftInputListener>(listener));
    }

    public void removeSoftInputListener(SoftInputListener listener){
        for (Iterator<WeakReference<SoftInputListener>> iterator = mListenerList.iterator();
             iterator.hasNext(); ) {
            WeakReference<SoftInputListener> weakRef = iterator.next();
            if (listener.equals(weakRef.get())){
                iterator.remove();
            }
        }
    }

    private void notifySoftInoutChangeListener(boolean show, int keyboardHeight){
        for (Iterator<WeakReference<SoftInputListener>> iterator = mListenerList.iterator();
             iterator.hasNext(); ) {
            WeakReference<SoftInputListener> weakRef = iterator.next();
            if (weakRef.get() != null){
                weakRef.get().onSoftInputChange(show, keyboardHeight);
            }
        }
    }

    public interface SoftInputListener {
        void onSoftInputChange(boolean show, int keyboardHeight);
    }

}
