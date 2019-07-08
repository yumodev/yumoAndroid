package com.yumo.demo.entry;

import android.text.TextUtils;

public class YmClass {
    Class<?> cls;
    private String mDisplayName;

    public static YmClass createInstance(Class<?> cls, String name){
        YmClass ymClass = new YmClass();
        ymClass.setCls(cls);
        ymClass.setDisplayName(name);
        return ymClass;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public String getDisplayName() {
        if (TextUtils.isEmpty(mDisplayName)){
            return cls.getSimpleName();
        }
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }
}
