package com.yumo.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by yumo on 2/3/16.
 */
public class YmV4Fragment extends Fragment implements IBackPress{

    protected FragmentInfo mFragmentInfo = null;

    @Override
    public void onStart() {
        super.onStart();
        if(getActivity() instanceof ISelectFragment){
            if (mFragmentInfo != null){
                ((ISelectFragment) getActivity()).setSelectFragment(mFragmentInfo);
            }
        }
    }

    @Override
    public boolean handleBackPress() {
        return false;
    }
}
