package com.yumo.ui.fragment;

import android.app.Fragment;

/**
 * Created by yumo on 2/3/16.
 */
public class YmFragment extends Fragment{

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
}
