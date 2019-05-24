package com.yumo.ui.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import androidx.annotation.MenuRes;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import com.yumo.common.log.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by yumodev on 17/11/25.
 */

public class YmMenuHelper {
    private final static String LOG_TAG = "menu:"+Log.LIB_TAG;
    private final static String XML_ITEM = "item";
    private final static String XML_ID = "id";
    private final static String XML_TITLE = "title";

    @SuppressLint("ResourceType")
    public static void inflateMenuRes(Context context, @MenuRes int menuRes, YmMenu menu){
        XmlResourceParser pullParser = null;
        try {
            pullParser = context.getResources().getXml(menuRes);
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
                        if (XML_ITEM.equals(tagName)){
                            YmMenuItem item = new YmMenuItem();
                            TypedArray a = context.obtainStyledAttributes(attrs, new int[0]);
                            for (int i = 0; i < pullParser.getAttributeCount(); i++){
                                String arrName = pullParser.getAttributeName(i);
                                int arrValue = pullParser.getAttributeResourceValue(i, 0);
                                if (XML_ID.equals(arrName)){
                                    item.setItemId(arrValue);
                                }else if (XML_TITLE.equals(arrName)){
                                    item.setTitle(context.getString(arrValue));
                                }
                                Log.i(LOG_TAG, arrName+" "+arrValue);
                            }
                            a.recycle();
                            menu.add(item);
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
