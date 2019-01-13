package com.yumo.android.test.architecture.mvp;

import com.yumo.android.test.architecture.mvp.ip.IpInfoFragment;
import com.yumo.android.test.architecture.mvp.ip.IpInfoTask;
import com.yumo.android.test.architecture.mvp.ip.IpInfpPresenter;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/9/7.
 */

public class MvpTestView extends YmTestFragment {

    public void testShowIp(){
        IpInfoFragment fragment = IpInfoFragment.newInstance();
        fragment.setPresenter(new IpInfpPresenter(fragment, IpInfoTask.getInstance()));
        showFragment(fragment);
    }
}
