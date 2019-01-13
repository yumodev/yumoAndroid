package com.yumo.android.test.net.leancloud;

import java.io.File;

/**
 * Created by yumodev on 17/1/9.
 */

public class LeanCloudDefine {
    public static final String APP_ID = "cw7mnl8g8wiosxsc33b3995r398nqhkyao6kzj8p4p513o4y";
    public static final String APP_KEY = "ghl8xb3oczx320as83k3jo61s5qsofhjl7pxe35e7wbix2ja";
    public static final String MASTER_KEY = "jy3jrl36p6fffwmp1mxm5rm19t84z3dq3okwtjurz4uqdugv";

    public static final String HOST = "https://api.leancloud.cn";
    public static final String URL_VERSION = "1.1";

    /**
     * 用户相关
     */
    public static final String URL_REGISTER = URL_VERSION + "/users";
    public static final String URL_ALL_USER = URL_VERSION + "/users";
    public static final String URL_LOGIN = URL_VERSION + "/login";
    public static final String URL_USER_INFO = URL_VERSION + "/users/me";
    public static final String URL_USER_DELETE = URL_ALL_USER+"/delete";

    /**
     * 对象相关
     */
    public static final String CLASS_HOST = HOST + File.separator + URL_VERSION + File.separator +"classes";

}
