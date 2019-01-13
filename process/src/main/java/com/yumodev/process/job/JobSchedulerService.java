package com.yumodev.process.job;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Toast;

import com.yumo.common.log.Log;
import com.yumodev.process.Define;

/**
 * Created by yumo on 2018/4/4.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {

    /**
     *
     * @param params
     * @return
     * true，表示该工作耗时，同时工作处理完成后需要调用onStopJob销毁（jobFinished）
     * false，任务运行不需要很长时间，到return时已完成任务处理
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(Define.mDebugLog, "onStartJob");
        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        //vibrator.vibrate(1000);
        Toast.makeText(getApplicationContext(), "Job", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
