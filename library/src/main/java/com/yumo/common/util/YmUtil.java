package com.yumo.common.util;

import java.util.UUID;

/**
 * Created by yumodev on 8/28/16.
 * 为分类的公用方法
 */
public class YmUtil {

    /**
     * 生成UUID
     * @return
     */
    public static String createUUID(){
        return UUID.randomUUID().toString();
    }
}
