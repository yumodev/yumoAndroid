package com.yumodev.test;

/**
 * Created by yumo on 2018/8/2.
 */

public interface OnPackageObserver {
    public void packageInstalled(String packageName, int returnCode);
    public void packageDeleted(String packageName,int returnCode);
}
