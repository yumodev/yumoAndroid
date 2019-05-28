package com.yumo.android.test.web.browser.utils;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.yumo.android.common.utils.BusEvent;
import com.yumo.android.test.web.browser.event.ReadModeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yumo on 4/6/16.
 */
public class RemodeJsCall implements IJsCall {
    public final String LOG_TAG = "RemodeJscall";
    public static final String JSCALL = "remode_js_call";

    public static String getPage(){
        String callJs = String.format("javascript:window.%s.getPage(document.getElementsByTagName('html')[0].innerHTML);", RemodeJsCall.JSCALL);
        return callJs;
    }

    public static String getBody(){
        String callJs = String.format("javascript:window.%s.getBody(document.body.innerHTML);", RemodeJsCall.JSCALL);
        return callJs;
    }


    public static String getReadBody(){
        String callJs = String.format("javascript:window.%s.getReadBody(document.body.innerHTML);", RemodeJsCall.JSCALL);
        return callJs;
    }

    public static String getReadPage(){
        String callJs = String.format("javascript:window.%s.getReadPage(document.getElementsByTagName('html')[0].innerHTML);", RemodeJsCall.JSCALL);
        return callJs;
    }

    public static String getTitle(){
        String callJs = String.format("javascript:window.%s.getTitle(document.title);", RemodeJsCall.JSCALL);
        return callJs;
    }

    public static String getURL(){
        String callJs = String.format("javascript:window.%s.getURL(document.URL);", RemodeJsCall.JSCALL);
        return callJs;
    }

    public static String getMxAppGetMainText(){
        String callJs = String.format("javascript:window.%s.MXAppGetMainText();", RemodeJsCall.JSCALL);
        return callJs;
    }

    @JavascriptInterface
    public void getPage(String html){
        Log.d(LOG_TAG, "getPage:"+html);
        BusEvent.getInstance().post(new ReadModeEvent(ReadModeEvent.TYPE_WEB_BODY, "<html>"+html+"</html>"));
    }

    @JavascriptInterface
    public void getBody(String body){
        Log.d(LOG_TAG, "getBody:"+body);
        BusEvent.getInstance().post(new ReadModeEvent(ReadModeEvent.TYPE_WEB_BODY, body));
        //Log.d(LOG_TAG, "\n"+generateThumb(body, 100));

    }

    @JavascriptInterface
    public void getReadBody(String body){
        Log.d(LOG_TAG, "getReadBody:"+body);
        BusEvent.getInstance().post(new ReadModeEvent(ReadModeEvent.TYPE_READ_BODY, body));
    }

    @JavascriptInterface
    public void getReadPage(String body){
        Log.d(LOG_TAG, "getReadBody:"+body);
        BusEvent.getInstance().post(new ReadModeEvent(ReadModeEvent.TYPE_READ_BODY, "<html>"+body+"</html>"));
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
    public void getMxAppGetMainText(String content){
        Log.d(LOG_TAG, "getMxAppGetMainText:"+content);
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
