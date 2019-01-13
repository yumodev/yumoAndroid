package com.yumodev.process.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by yumo on 2018/5/23.
 */

public class UmengAnalytics {
    private final String LOG_TAG = "UmengAnalytics";
    private Context mContext = null;
    private UmengAnalytics(){

    }

    public static UmengAnalytics getInstance(){
        return SingltonHandler.mInstance;
    }

    public void init(Context context){
        mContext = context;


        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(mContext, "5b04cdff8f4a9d46a3000082", "test", MobclickAgent.EScenarioType.E_UM_NORMAL, true);
        MobclickAgent.startWithConfigure(config);
        MobclickAgent.setDebugMode(true);
    }

    private static final class SingltonHandler{
        private static final UmengAnalytics mInstance = new UmengAnalytics();
    }
}
