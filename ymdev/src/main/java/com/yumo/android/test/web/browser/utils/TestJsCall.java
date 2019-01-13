package com.yumo.android.test.web.browser.utils;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yumo on 4/6/16.
 */
public class TestJsCall implements IJsCall {
    public final String LOG_TAG = "TestJsCall";
    public static final String JSCALL = "test_js_call";

    public static String getBodyHtml(){
        String callJs = String.format("javascript:window.%s.getBodyHtml(document.body.innerHTML);", TestJsCall.JSCALL);
        return callJs;
    }

    public static String getTitle(){
        String callJs = String.format("javascript:window.%s.getTitle(document.title);", TestJsCall.JSCALL);
        return callJs;
    }

    public static String getURL(){
        String callJs = String.format("javascript:window.%s.getURL(document.URL);", TestJsCall.JSCALL);
        return callJs;
    }

    public static String getDescription(){
       // String callJs =
        return "";
    }

    @JavascriptInterface
    public void getBodyHtml(String body){
        Log.d(LOG_TAG, "getBodyHtml:"+body);
        //Log.d(LOG_TAG, "\n"+generateThumb(body, 100));

    }

    @JavascriptInterface
    public void getTitle(String body){
        Log.d(LOG_TAG, "getTitle:"+body);
    }

    @JavascriptInterface
    public void getURL(String body){
        Log.d(LOG_TAG, "getUrl:"+body);
    }

    @JavascriptInterface
    public void getDescription(String json){
        Log.d(LOG_TAG, "getDescrition:"+json);
    }


    /**
     * 从正文中获取摘要
     */
    public String generateThumb(String content, int length){
        String regEx = "<[^>]+>";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
            content = matcher.replaceAll("");
            content = content.replaceAll(" ","");
            content = content.replaceAll("\\n", "");
            content = content.replaceAll("\\t", "");
        }else{
            Log.d(LOG_TAG, "not find");
        }

        if (content.length() <= length){
            return content;
        }
        return content.substring(0, length);
    }



}
