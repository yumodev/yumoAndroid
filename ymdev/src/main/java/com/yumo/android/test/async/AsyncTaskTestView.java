package com.yumo.android.test.async;

import android.os.AsyncTask;
import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by yumo on 15/11/22.
 * 测试异步的AsyncTask
 */
public class AsyncTaskTestView extends YmTestFragment {

    public void testTask(){
        for (int n =0; n < 200; n++){
            TestAsyncTask task = new TestAsyncTask();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor)AsyncTask.THREAD_POOL_EXECUTOR;
            Log.d("AsyncTaskTestView", "taskCount:" + poolExecutor.getTaskCount() + "activeCount:" + poolExecutor.getActiveCount());
        }
    }

    public void testShowAsyncTaskProgressBar(){
        showFragment(new AsyncTaskProgressFragment());
    }

    class TestAsyncTask extends AsyncTask<String, Integer , String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }




}
