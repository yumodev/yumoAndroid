package com.yumo.demo.entry;

import java.lang.reflect.Method;

/**
 * Created by yumodev on 17/2/15.
 */

public class YmMethod {
    private Method method;
    private String mDisplayName;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }
}
