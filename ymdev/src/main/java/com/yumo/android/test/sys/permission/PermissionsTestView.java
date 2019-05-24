package com.yumo.android.test.sys.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yumo.common.android.PermissionUtil;
import com.yumo.common.android.YmToastUtil;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yumodev on 17/9/25.
 * 权限测试类
 */

public class PermissionsTestView extends YmTestFragment {

    final int REQUEST_CODE = 66;
    /**
     * 进入应用详情页面
     */
    public void testOpenAppDetailsActivity(){
        Intent intent = new Intent()
                .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package", getContext().getPackageName(), null));

        try {
            getContext().startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 通过RxPermissions获取权限
     */
    public void testRxPermissionsCamera(){
        RxPermissions rxPermissions = new RxPermissions(this.getActivity());
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onComplete() {
                        //showToastMessage("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value){
                            showToastMessage("赋予权限");
                        }else{
                            showToastMessage("拒绝权限");
                        }
                    }
                });

    }

    /**
     * 通过RxPermissions获取权限
     */
    public void testRxPermissionsSD(){
        RxPermissions rxPermissions = new RxPermissions(this.getActivity());
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onComplete() {
                        //showToastMessage("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value){
                            showToastMessage("赋予权限");
                        }else{
                            showToastMessage("拒绝权限");
                        }
                    }
                });

    }

    /**
     * 同时申请多个权限
     */
    public void testRxRequestMore() {
        //同时请求多个权限
        RxPermissions rxPermissions = new RxPermissions(this.getActivity());
        rxPermissions.request(Manifest.permission.RECEIVE_MMS,
                        Manifest.permission.READ_CALL_LOG)//多个权限用","隔开
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean value) {
                        if (value){
                            showToastMessage("赋予权限");
                        }else{
                            showToastMessage("未赋权限");
                        }
                    }
                });

    }

    /**
     * 分别获取权限
     */
    public void testRequestEach(){
        RxPermissions rxPermissions = new RxPermissions(this.getActivity());
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            //当ACCESS_FINE_LOCATION权限获取成功时，permission.granted=true
                            Log.i("permissions", Manifest.permission.ACCESS_FINE_LOCATION + "：" + permission.granted);
                        }else if (permission.name.equals(Manifest.permission.RECORD_AUDIO)) {
                            //当RECORD_AUDIO 权限获取成功时，permission.granted=true
                            Log.i("permissions", Manifest.permission.RECORD_AUDIO + "：" + permission.granted);
                        }else if (permission.name.equals(Manifest.permission.CAMERA)) {
                            //当CAMERA权限获取成功时，permission.granted=true
                            Log.i("permissions", Manifest.permission.CAMERA + "：" + permission.granted);
                        }
                    }
                });
    }

    public void testRxRequestPermissions(){
        PermissionUtil.requestPermission(getActivity());
    }

    /**
     * 是否有存储权限
     */
    public void testHasSdPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }

    /**
     * 是否有相机权限。
     */
    public void testHasCameraPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }

    /**
     * 是否有电话权限。
     */
    public void testHasPhonePermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }

    /**
     * 是否有位置权限。
     */
    public void testHasLocationPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }

    /**
     * 是否有联系人权限
     */
    public void testHasContactsPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }

    /**
     * 是否有短信权限
     */
    public void testHasSmsPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }


    /**
     * 是否有日历
     */
    public void testHasCalendarPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }


    /**
     * 是否有传感器权限
     */
    public void testHasSendorsPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BODY_SENSORS);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }else{
            showToastMessage("未授权");
        }
    }


    public void testRquestSdPermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    public void testShould(){
       boolean result =  ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        YmToastUtil.showMessage("请求SD卡："+result);
    }

    public void testRquestMorePermission(){
        int granted = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (granted == PackageManager.PERMISSION_GRANTED){
            showToastMessage("已授权");
        }

        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_CALENDAR
        };

        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE){
            for (int i  = 0; i < permissions.length; i++){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    showToastMessage("已授权"+permissions[i]);
                }else{
                    showToastMessage("已拒绝"+permissions[i]);
                }
            }
        }
    }






}
