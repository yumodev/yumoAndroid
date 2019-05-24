package com.yumo.common;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.d("test", appContext.getPackageName());

        //assertEquals("com.yumodev.common.test", appContext.getPackageName());
    }


    private String getLoginName(){
        return "yumodev";
    }

    @Test
    public void getBodyString() throws Exception {

//        String url = "https://api.github.com/users/"+getLoginName();
//        String result = "";
//        try {
//            result = YmOkHttpUtil.getBodyString(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (result != null){
//            System.out.println(result);
//        }
    }
}
