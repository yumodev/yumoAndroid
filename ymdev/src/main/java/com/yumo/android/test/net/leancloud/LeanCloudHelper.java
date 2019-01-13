package com.yumo.android.test.net.leancloud;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yumodev on 17/1/9.
 */

public class LeanCloudHelper {

    public static Map<String, String> getCommonHeader(){
        Map<String, String> headers = new HashMap<>();
        headers.put("X-LC-Id", LeanCloudDefine.APP_ID);
        headers.put("X-LC-Key", LeanCloudDefine.APP_KEY);
        headers.put("Content-Type", "application/json");
        return headers;
    }


    public static Map<String, String> getCommonHeader(String token){
        Map<String, String> headers = new HashMap<>();
        headers.put("X-LC-Id", LeanCloudDefine.APP_ID);
        headers.put("X-LC-Key", LeanCloudDefine.APP_KEY);
        headers.put("X-LC-Session", token);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    /**
     * 获取注册接口地址
     * @return
     */
    public static String getRegisterUser(){
        return LeanCloudDefine.HOST + "/" +LeanCloudDefine.URL_REGISTER;
    }

    /**
     * 登录用户
     * @return
     */
    public static String getLogin(){
        return LeanCloudDefine.HOST + "/" +LeanCloudDefine.URL_LOGIN;
    }

    /**
     * 获取用户登录信息
     * @return
     */
    public static String getUserInfo(){
        return LeanCloudDefine.HOST + "/" +LeanCloudDefine.URL_USER_INFO;
    }

    /**
     * 获取所有用户信息接口
     * @return
     */
    public static String getAllUser(){
        return LeanCloudDefine.HOST + "/" +LeanCloudDefine.URL_ALL_USER;
    }

    /**
     * 删除用户
     * @return
     */
    public static String getDeleteUser(){
        return LeanCloudDefine.HOST + "/" + LeanCloudDefine.URL_USER_DELETE;
    }

    public static String createNote(){
        return LeanCloudDefine.CLASS_HOST + File.separator + "note";
    }
}
