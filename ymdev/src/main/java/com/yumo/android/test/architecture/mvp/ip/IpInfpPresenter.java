package com.yumo.android.test.architecture.mvp.ip;

/**
 * Created by yumodev on 17/9/7.
 */

public class IpInfpPresenter implements IpInfpContract.Presenter, LoadTasksCallBack<IpInfo> {
    private IpInfpContract.View taskView;
    private NetTask netTask;
    public IpInfpPresenter(IpInfpContract.View taskView, NetTask netTask){
        this.taskView = taskView;
        this.netTask = netTask;

    }
    @Override
    public void onSuccess(IpInfo ipInfo) {
        if (taskView.isActive()){
            taskView.setIpInfo(ipInfo);
        }
    }

    @Override
    public void onStart() {
        if (taskView.isActive()){
            taskView.showLoading();
        }
    }

    @Override
    public void onFailed() {
        if (taskView.isActive()){
            taskView.showError();
            taskView.hideLoading();
        }
    }

    @Override
    public void onFinish() {
        if (taskView.isActive()){
            taskView.hideLoading();
        }
    }

    @Override
    public void getIoInfo(String ip) {
        netTask.execute(ip, this);
    }
}
