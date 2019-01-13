package com.yumo.android.test.architecture.mvp.ip;

/**
 * Created by yumodev on 17/9/7.
 */

public class IpInfo {
    private int code;
    private IpData data;

    public int getCode() {
        return code;
    }

    public IpData getData() {
        return data;
    }

    public void setData(IpData data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
