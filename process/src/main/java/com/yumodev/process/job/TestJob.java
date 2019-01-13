package com.yumodev.process.job;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/4/4.
 */

public class TestJob extends YmTestFragment {
    public void testA(){
        showToastMessage("Hello");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void testStartJob(){
        JobScheduler jobScheduler = (JobScheduler)getContext().getSystemService(Service.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getContext().getPackageName(), JobSchedulerService.class.getName()));
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);//有无网络都可以
        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT){
//            builder.setMinimumLatency(5000);
//            builder.setOverrideDeadline(10000);
            builder.setPeriodic(5000);//每隔5秒执行一次
        }else {
            builder.setPeriodic(5000);//每隔5秒执行一次
        }

        try {
            if(jobScheduler.schedule(builder.build()) == JobScheduler.RESULT_FAILURE){
                showToastMessage("创建JobScheduler失败");
            }else{
                showToastMessage("创建JobScheduler 成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            showToastMessage(e.getMessage());
        }

    }

}
