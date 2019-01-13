package com.yumo.android.test.net.leancloud;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yumo.android.R;
import com.yumo.android.test.entry.User;
import com.yumo.common.thread.YmHandlerThreadUtil;
import com.yumo.common.log.Log;
import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.view.YmTestFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yumodev on 17/11/17.
 */

public class TestLeanCloudUserView extends YmTestFragment {
    private final String LOG_TAG = Log.LIB_TAG;

    private AutoCompleteTextView mEmailView = null;
    private EditText mPassWordView = null;
    private AutoCompleteTextView mUserNameView = null;
    private AutoCompleteTextView mPhoneView = null;
    /**
     * 用户成功登陆后返回的Token
     */
    private User mCurrentUser;

    @Override
    protected View getHeaderView() {
        View view = View.inflate(getContext(), R.layout.login_view, null);
        mEmailView =  view.findViewById(R.id.email);
        mEmailView.setText("yumotingyu@gmail.com");
        mPassWordView = view.findViewById(R.id.password);
        mPassWordView.setText("leanclound1988");
        mPhoneView = view.findViewById(R.id.phone);
        mPhoneView.setText("18001390124");
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
        return user;
    }

    public void testRegisterUser(){
        final User user = getUser();
        YmHandlerThreadUtil.getInstance().post(new Runnable() {
            @Override
            public void run() {
                JSONObject bodyJson = new JSONObject();
                String body = "";
                try {
                    bodyJson.put("username", user.username);
                    bodyJson.put("password", user.password);
                    if (!TextUtils.isEmpty(user.email)){
                        bodyJson.put("email", user.email);
                    }

                    if (!TextUtils.isEmpty(user.phone)){
                        bodyJson.put("phone", user.phone);
                    }
                    body = bodyJson.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String result = "";
                try {
                    result = YmOkHttpUtil.postString(LeanCloudHelper.getRegisterUser(), body, LeanCloudHelper.getCommonHeader());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(LOG_TAG, "register body:"+body+" result:"+result);
                showToastMessageOnUiThread(result);
            }
        });

        /**
         * {"sessionToken":"9h6ce7thkma3irpoan9ga99to","updatedAt":"2017-01-10T05:15:09.939Z","phone":"18001390124","objectId":"58746ddd8d6d810058a05d8c","username":"test","createdAt":"2017-01-10T05:15:09.939Z","emailVerified":false,"mobilePhoneVerified":false}
         */

    }

    /**
     * 登录用户
     */
    public void testLogin(){
        final User user = getUser();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                try {
                    JSONObject bodyJson = new JSONObject();
                    bodyJson.put("username", user.username);
                    bodyJson.put("password", user.password);

                    String result = YmOkHttpUtil.postString(LeanCloudHelper.getLogin(), bodyJson.toString(), LeanCloudHelper.getCommonHeader());
                    mCurrentUser = (new Gson()).fromJson(result, User.class);
                    e.onNext(result);
                    e.onComplete();
                } catch (IOException ee) {
                    ee.printStackTrace();
                    e.onError(ee);
                } catch (JSONException ee){
                    ee.printStackTrace();
                    e.onError(ee);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull String value) {
                        Log.i(LOG_TAG, "onNext:" + value);
                        showToastMessageOnUiThread("登录成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToastMessageOnUiThread(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(LOG_TAG, "onComplete");
                        Log.i(LOG_TAG, Thread.currentThread().getName());
                    }
                });
    }

    /**
     * 获取已登录用户的信息
     */
    public void testLoginInfo(){
        final User user = mCurrentUser;
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                try {
                    String result = YmOkHttpUtil.getBodyString(LeanCloudHelper.getUserInfo(), LeanCloudHelper.getCommonHeader(user.sessionToken));
                    mCurrentUser = (new Gson()).fromJson(result, User.class);
                    e.onNext(result);
                    e.onComplete();
                } catch (IOException ee) {
                    ee.printStackTrace();
                    e.onError(ee);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull String value) {
                        Log.i(LOG_TAG, "onNext:" + value);
                        showToastMessageOnUiThread("成功获取用户信息："+value);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToastMessageOnUiThread(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(LOG_TAG, "onComplete");
                        Log.i(LOG_TAG, Thread.currentThread().getName());
                    }
                });
    }


    /**
     * 获取所有用户
     */
    public void testGetAllUsers(){
        YmHandlerThreadUtil.getInstance().post(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    result = YmOkHttpUtil.getBodyString(LeanCloudHelper.getAllUser(), LeanCloudHelper.getCommonHeader());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (result != null){
                    Log.i(LOG_TAG, "register result:"+result);
                    showToastMessageOnUiThread(result);
                }else{
                    showToastMessageOnUiThread("failed");
                }
            }
        });
    }
}
