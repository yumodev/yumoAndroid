package com.yumodev.process.test.afinal;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import net.tsz.afinal.FinalDb;

import java.util.Date;
import java.util.List;

/**
 * Created by yumo on 2018/4/23.
 */

public class TestAfinal extends YmTestFragment {
    public final String LOG_TAG = "TestAfinal";
    FinalDb mDb = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = FinalDb.create(getContext());
    }

    public void testA(){

    }

    public void testAddUser(){
        FinalUser user = new FinalUser();
        user.setEmail("afinal@tsz.net");
        user.setName("探索者");
        user.setRegisterDate(new Date());

        mDb.save(user);
    }

    public void testShowUser(){
        List<FinalUser> dataList = mDb.findAll(FinalUser.class);
        for (FinalUser user : dataList){
            Log.i(LOG_TAG, user.getEmail() + " " + user.getName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
