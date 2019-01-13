package com.yumo.common.net;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by yumodev on 17/3/9.
 */
@RunWith(AndroidJUnit4.class)
public class YmOkHttpUtilTest  {

    private String getLoginName(){
        return "yumodev";
    }

    @Test
    public void getBodyString() throws Exception {
        String url = "https://api.github.com/users/"+getLoginName();
        String result = null;
        try {
            result = YmOkHttpUtil.getBodyString(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertNotNull(result);

    }

}