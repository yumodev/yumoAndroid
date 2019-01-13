package com.yumo.android.common;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yumo.ui.util.YmToast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yumodev on 17/11/28.
 */

public class AppDevUtil {

    public static void requestPermission(Activity activity, String permission){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .request(permission)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onComplete() {
                        //showToastMessage("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        YmToast.showToast(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value){
                            YmToast.showToast("赋予权限");
                        }else{
                            YmToast.showToast("未赋权限");
                        }
                    }
                });
    }
}
