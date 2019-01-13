package com.yumo.common.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * OkHttp的Cookie管理。
 */
public class YmCookieManager implements CookieJar {
    //保存每个url的cookie
    private HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

    //上一个请求url
    private HttpUrl url;

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        //保存链接的cookie
        cookieStore.put(httpUrl, list);
        //保存上一次的url，供给下一次cookie的提取。
        url = httpUrl;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {

        //加载上一个链接的cookie
        List<Cookie> cookies = cookieStore.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
