package com.yumo.android.test.io;

import android.annotation.SuppressLint;
import android.content.res.XmlResourceParser;
import androidx.annotation.MenuRes;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import com.yumo.android.R;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

/**
 * Created by yumodev on 5/11/16.
 * xml文件的解析的demo
 */
public class XmlTest extends YmTestFragment {
    private final String LOG_TAG = Log.LIB_TAG;

    public void testPullParseXml(){
        String xmlFileName = "xml/favorites_short.xml";
        InputStream is = null;
        try {
            is = getContext().getAssets().open(xmlFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (is == null){
            showToastMessage(xmlFileName+" is not exist!");
        }

        XmlPullParserFactory xmlFactory = null;
        XmlPullParser pullParser = null;
        try {
            xmlFactory = XmlPullParserFactory.newInstance();
            pullParser = xmlFactory.newPullParser();
            pullParser.setInput(is, null);

            int event = pullParser.getEventType();
            Log.d(LOG_TAG, event+"");
            Stack<String> fStack = new Stack<>();
            int level = 1;
            while (event != XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "start_document");
                        break;
                    case XmlPullParser.START_TAG:{
                        String tagName = pullParser.getName();
                        if (tagName.equals("f")){
                            String title = pullParser.getAttributeValue(null, "t");
                            Log.d(LOG_TAG, String.format("start_tag:%s, title: %s, level:%d", tagName, title, level++));
                            fStack.push(title);
                        }else if(tagName.equals("e")){
                            String title = pullParser.getAttributeValue(null, "t");
                            Log.d(LOG_TAG, String.format("start_tag:%s, folder: %s, title:%s", tagName, fStack.peek(), title));
                        }else {
                            Log.d(LOG_TAG, "start_tag:"+tagName);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        Log.d(LOG_TAG, "end_tag:"+pullParser.getName());
                        String tagName = pullParser.getName();
                        if (tagName.equals("f")){
                            fStack.pop();
                            level --;
                        }
                        break;
                    }
//                    case XmlPullParser.TEXT:{
//                        Log.d(LOG_TAG, "text"+pullParser.getText());
//                    }

                }
                event = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void testParseMenuXml(){
        inflateMenuRes(R.menu.webview_menu_url);
    }

    @SuppressLint("ResourceType")
    private void inflateMenuRes(@MenuRes int menuRes){
        XmlResourceParser pullParser = null;
        try {
            pullParser = getContext().getResources().getXml(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(pullParser);
            int event = pullParser.getEventType();
            Log.d(LOG_TAG, event+"");
            while (event != XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_DOCUMENT: {
                        Log.d(LOG_TAG, "start_document");
                        break;
                    }
                    case XmlPullParser.START_TAG:{
                        String tagName = pullParser.getName();
                        Log.d(LOG_TAG, "start_tag:"+tagName+" "+pullParser.getAttributeCount());
                        for (int i = 0; i < pullParser.getAttributeCount(); i++){
                            String arrName = pullParser.getAttributeName(i);
                            String arrValue = pullParser.getAttributeValue(i);
                            Log.i(LOG_TAG, arrName+" "+arrValue);
                        }

                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        Log.d(LOG_TAG, "end_tag:"+pullParser.getName());
                        String tagName = pullParser.getName();
                        break;
                    }
                    case XmlPullParser.TEXT:{
                        Log.d(LOG_TAG, "text"+pullParser.getText());
                    }
                }
                event = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            throw new InflateException("Error inflating menu XML", e);
        } catch (IOException e) {
            throw new InflateException("Error inflating menu XML", e);
        } finally {
            if (pullParser != null) pullParser.close();
        }
    }
}
