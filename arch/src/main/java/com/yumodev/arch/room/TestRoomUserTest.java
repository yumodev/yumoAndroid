package com.yumodev.arch.room;

import android.arch.persistence.room.Delete;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.arch.Define;
import com.yumodev.arch.R;

import java.util.List;

/**
 * Created by yumodev on 17/12/6.
 */

public class TestRoomUserTest extends YmTestFragment {
    private AutoCompleteTextView mEmailView = null;
    private EditText mPassWordView = null;
    private AutoCompleteTextView mUserNameView = null;
    private AutoCompleteTextView mPhoneView = null;

    @Override
    protected View getHeaderView() {
        View view = View.inflate(getContext(), R.layout.login_view, null);
        mEmailView =  view.findViewById(R.id.email);
        mEmailView.setText("yumotingyu@gmail.com");
        mPassWordView = view.findViewById(R.id.password);
        mPassWordView.setText("password");
        mPhoneView = view.findViewById(R.id.phone);
        mPhoneView.setText("180001390000");
        mUserNameView = view.findViewById(R.id.name);
        mUserNameView.setText("yumodev");
        return view;
    }

    private User getUser(){
        User user = new User();
        user.username = mUserNameView.getText().toString();
        user.phone = mPhoneView.getText().toString();
        user.email = mEmailView.getText().toString();
        user.password = mPassWordView.getText().toString();
        user.createAt = user.updateAt = System.currentTimeMillis();
        return user;
    }

    /**
     * 插入用户
     */
    public void testInsertUser(){
        final User user = getUser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDataBase.getDatabase(getContext()).userDao().insertAll(user);
            }
        }).start();
    }

    public void testPrintAllData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> datas = AppDataBase.getDatabase(getContext()).userDao().getAll();
                for (User user : datas){
                    Log.i(Define.LOG_ROOM, user.toString());
                }
            }
        }).start();
    }
}
