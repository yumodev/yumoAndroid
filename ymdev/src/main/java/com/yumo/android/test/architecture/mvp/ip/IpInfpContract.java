package com.yumo.android.test.architecture.mvp.ip;

/**
 * Created by yumodev on 17/9/7.
 */

public interface IpInfpContract {
    interface  Presenter{
        void getIoInfo(String ip);
    }

    interface View extends BaseView<Presenter>{
        void setIpInfo(IpInfo ipInfo);
        void showLoading();
        void hideLoading();
        void showError();
        boolean isActive();
    }
}
