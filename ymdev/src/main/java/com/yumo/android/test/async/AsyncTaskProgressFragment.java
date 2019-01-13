package com.yumo.android.test.async;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yumo.android.R;

/**
 * Created by yumo on 4/2/16.
 */
public class AsyncTaskProgressFragment extends Fragment {
    private final String LOG_TAG = "AsyncTaskProgress";
    private ProgressBar mProgressBar = null;
    private TestAsyncTask mAsyncTask = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asynctask_demo_page, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar = (ProgressBar)getView().findViewById(R.id.progressbar);
        getView().findViewById(R.id.begin_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAsyncTask = null;
                if (mAsyncTask == null){
                    mAsyncTask = new TestAsyncTask();
                }
                mAsyncTask.execute();
            }
        });

        getView().findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mAsyncTask != null){
                    mAsyncTask.onCancelled();
                }
            }
        });
    }

    class TestAsyncTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            Log.d(LOG_TAG, "onPreExecute");
            mProgressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            int n = 0;
            while (true) {
                publishProgress(n++);
                if (n > 100) break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d(LOG_TAG, "onPostExecute");
            if (mProgressBar.getProgress() < 100) {
                Toast.makeText(getContext().getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            Log.d(LOG_TAG,  "onCancelled");
            if (mProgressBar.getProgress() < 100) {
                Toast.makeText(getContext().getApplicationContext(), "pause", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            Log.d(LOG_TAG, "onCancelled boolean:" + result);
            if (mProgressBar.getProgress() < 100) {
                Toast.makeText(getContext().getApplicationContext(), "pause", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
