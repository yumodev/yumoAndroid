package com.yumodev.ui.module.stack;

import android.util.Log;

import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/11.
 */

public class StackHelper {
    public float mMaxScale = 0.9f;
    public float mMinScale = 0.7f;
    public float mSetepRate = 0.2f;
    public float mMinProgrss = 0.2f;
    public float mMinPositiveProgress = 0.4f;
    public float mMaxPositiveProgress = 0.5f;
    public float mMaxProgrss = 0.8f;
    public float mStackProgress = 0.6f;
    public float mDeltaInstance;
    public int mStackHeight = 0;

    public StackHelper(){

    }

    public void initConfig(int height){
        mStackHeight = height;
        mDeltaInstance = mStackHeight * mMinProgrss;
        mStackProgress = mMinProgrss;
    }

    public float getTransLayY(int index, float progress){
        float curProgress = mSetepRate * index + progress;

        if (curProgress < mMinProgrss){
            curProgress = mMinProgrss;
        }

        return mStackHeight * curProgress;
    }

    public float getScale(int index, float progress){
        float range = mMaxScale - mMinScale;
        return mMinScale + range * (mSetepRate * index + progress);
    }

    public float getProgress(float deltaInstance){
        return  deltaInstance / mStackHeight;
    }

    public void setProgress(float progress){
        mStackProgress = progress;
    }
    public float getProgress(){
        return  mStackProgress;
    }

    public boolean isOverPositiveScrollP(){
        Log.i(Define.INSTANCE.getLOG_TAG(), "isOverPositiveScrollP:"+mStackProgress);
        return (mStackProgress < mMaxPositiveProgress && mStackProgress > mMinPositiveProgress);
    }

    /**
     * 计算阻尼，当超过我们设定的位置时，让用户在滑动的时候感到“吃力”
     * @return
     */
    public float calculateDamping(){
        float damping = (0.5f - Math.abs(mStackProgress - getPositiveScrollP()) * 5);
        Log.i(Define.INSTANCE.getLOG_TAG(), "calculateDamping :: damping = :" + damping+" "+mStackProgress);
        return damping;
    }

    /**
     * 根据滑动的进度来判断手指释放后需要自动回滚的目标进度
     */
    private float getPositiveScrollP() {
        if (mStackProgress > mMinPositiveProgress) {
            return mMinPositiveProgress;
        } else if(mStackProgress > mMaxPositiveProgress){
            return mMaxPositiveProgress;
        }
        return mStackProgress;
    }

}
