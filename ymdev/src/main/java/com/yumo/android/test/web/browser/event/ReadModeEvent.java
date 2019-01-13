package com.yumo.android.test.web.browser.event;

/**
 * Created by yumo on 5/10/16.
 */
public class ReadModeEvent {

    public static final int TYPE_WEB_BODY = 0;
    public static final int TYPE_READ_BODY = 1;

    private int mBodyType;
    private String mBody;
    public ReadModeEvent(int bodyType, String body){
        mBodyType = bodyType;
        mBody = body;
    }

    public int getBodyType(){
        return mBodyType;
    }

    public String getBody(){
        return mBody;
    }


}
