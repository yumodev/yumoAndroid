package com.yumo.demo.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yumo.demo.R;
import com.yumo.demo.base.YmDemoBaseFragment;
import com.yumo.demo.config.Config;
import com.yumo.demo.listener.UpdateTitleObservable;


public class YmTestFragment extends YmDemoBaseFragment {
    protected static final String TEST_TAG = "TestFragment";
    private YmTestView mTestView = null;
    private int mToolbarVisible = View.VISIBLE;
    @Override
    protected View getContainerView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String tag = getTag();
        if (TextUtils.isEmpty(tag)){
            mToolbar.setTitle(TEST_TAG);
            UpdateTitleObservable.getInstance().setTitle(TEST_TAG);
        }else{
            mToolbar.setTitle(tag);
            UpdateTitleObservable.getInstance().setTitle(tag);
        }

        mToolbar.setNavigationIcon(R.drawable.head_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        mTestView = new YmTestView(getContext());
        mTestView.init(this, getClass(), getHeaderView(), getFooterView());
        return mTestView;
    }

    /**
     * 获取头部View
     * @return
     */
    protected View getHeaderView(){
        return null;
    }

    /**
     * 获取尾部View
     * @return
     */
    protected View getFooterView(){
        return null;
    }

    public boolean addHeaderView(View view){
        if (mTestView == null){
            return false;
        }
       return mTestView.addHeaderView(view);
    }

    public boolean addFooterView(View view){
        if (mTestView == null){
            return false;
        }
        return mTestView.addFooterView(view);
    }

    public Context getContext() {
        return getActivity();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return false;
    }

    public void showTestView(View view, FrameLayout.LayoutParams layoutParams) {
        TestCaseFragment testCase = new TestCaseFragment();
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.addView(view, layoutParams);
        testCase.setContentView(frameLayout);

        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.test_fragment_id, testCase).commit();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.test_fragment_id, fragment).commit();
    }

    public void showDialogFragment(DialogFragment fragment, String tag) {
        fragment.show(getActivity().getSupportFragmentManager(), tag);
    }

    public void showTestView(View view) {
        showTestView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    public void showTestView(int resid) {
        View view = View.inflate(getActivity(), resid, null);
        showTestView(view);
    }

    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showToastMessageOnUiThread(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class TestCaseFragment extends Fragment {
        private View mContent;

        public void setContentView(View v) {
            mContent = v;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            FrameLayout root = new FrameLayout(getActivity());
            root.addView(mContent, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            return root;
        }
    }

    public void printLog(String logTag, String info) {
        System.out.println(logTag + " " + info);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            mToolbarVisible = bundle.getInt(Config.ARGUMENT_TOOLBAR_VISIBLE);
            if (mToolbar != null){
                mToolbar.setVisibility(mToolbarVisible);
            }
        }
    }
}
