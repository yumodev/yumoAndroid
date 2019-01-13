package com.yumo.android.test.web;

import android.util.Log;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by guo on 16/1/13.
 */
public class JsoupTestView extends YmTestFragment {
    private final String LOG_TAG = "JsoupTestView";

    private Stack<String> mFolders = new Stack<>();

    private Stack<Element> mFolderElement = new Stack<>();
    private Map<Element, Element> mFolderMap = new HashMap<>();

    public  void testChrome() {
        InputStream inputStream = getResources().openRawResource(R.raw.bookmarks_chrome);

        try {
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");
            Log.d(LOG_TAG, doc.title());
            Element dl = doc.select("dl").first();
            printElementInfo(dl);
            printElementInfo(doc.select("dl").get(1));

            traversingUrl(dl);

//            Elements elements = null;
//            //Elements elements = doc.getAllElements();
//            for (int i = 0; i < elements.size(); i++){
//                Log.d(LOG_TAG, "elements i :"+ i + " :" + elements.get(i).nodeName());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testraversingUrls() {
        InputStream inputStream = getResources().openRawResource(R.raw.bookmarks_chrome);

        try {
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");
            Element element = doc.select("dl").get(1);
            printElementInfo(doc.select("dl").get(1));

            traversingUrl(element);
//
//            Elements elements = element.children();
//            for (int i = 0; i < elements.size(); i++){
//                Log.d(LOG_TAG, "elements i :"+ i + " :" + elements.get(i).nodeName());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testChromeFolders(){
        InputStream inputStream = getResources().openRawResource(R.raw.bookmarks_chrome);
        try {
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");
            Log.d(LOG_TAG, doc.title());
            Elements elements = doc.select("editor_h3").clone();

            for (int i = 0; i < elements.size(); i++){
                Element element = elements.get(i);
                Log.d(LOG_TAG, "folder: name:"+element.text() +" create time:"+ element.attr("add_date") + " last_modify:"+ element.attr("last_modified"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testChromeUrls(){
        InputStream inputStream = getResources().openRawResource(R.raw.bookmarks_chrome);
        try {
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");
            Log.d(LOG_TAG, doc.title());
            Elements elements = doc.select("a").clone();

            for (int i = 0; i < elements.size(); i++){
                Element element = elements.get(i);
                Log.d(LOG_TAG, "folder: name:"+element.text() +" url:"+ element.attr("href")+" create time:"+ element.attr("add_date"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void traversingUrl(Element parentElement){
        Elements elements = parentElement.children();
        for (int i = 0; i < elements.size(); i++){
            Element element = elements.get(i);

            if (element.tag() == null){
                Log.d(LOG_TAG, "element.tag is null");
                continue;
            }

            //Log.d(LOG_TAG, "element tag: "+element.tagName());
            if (element.tag().equals("p")){
                continue;
            }

            if (element.tagName().equals("dl")){
                traversingUrl(element);
            }

            if (element.tagName().equals("dt")){
                traversingUrl(element);
            }

            if (element.tagName().equals("editor_h3")){
                //traversingUrl(element);
                if (mFolderElement.size() > 0){
                    mFolderMap.put(element, mFolderElement.peek());
                }
                Log.d(LOG_TAG, "folder: name:"+element.text() +" create time:"+ element.attr("add_date") + " last_modify:"+ element.attr("last_modified"));
                mFolders.push(element.text());
            }

            if (element.tagName().equals("a")){
                Log.d(LOG_TAG, "folder: name:"+element.text() +" url:"+ element.attr("href")+" create time:"+ element.attr("add_date") + " last_modify:"+ element.attr("last_modified"));
            }

           // Log.d(LOG_TAG, "elements i :"+ i + " :" + elements.get(i).nodeName());
        }
    }





    private void traversingDl(Element parentElement){
        Elements elements = parentElement.children();
        for (int i = 0; i < elements.size(); i++){
            Element element = elements.get(i);

            if (element.tag() == null){
                Log.d(LOG_TAG, "element.tag is null");
                continue;
            }

            //Log.d(LOG_TAG, "element tag: "+element.tagName());
            if (element.tag().equals("p")){
                continue;
            }

            if (element.tagName().equals("dt")){
                //traversingUrl(element);

                Elements dtChilds = element.children();

                for (int n = 0; n < dtChilds.size(); n++){
                    if (element.tagName().equals("editor_h3")){
                        //traversingUrl(element);
                        Log.d(LOG_TAG, "folder: name:"+element.text() +" create time:"+ element.attr("add_date") + " last_modify:"+ element.attr("last_modified"));
                        mFolders.push(element.text());
                    }

                    if (element.tagName().equals("a")){
                        Log.d(LOG_TAG, "folder: name:" + element.text() + " url:" + element.attr("href") + " create time:" + element.attr("add_date") + " last_modify:" + element.attr("last_modified"));
                    }

                    if (element.tagName().equals("dl")){
                        traversingUrl(element);
                    }

                }
            }


            if (element.tagName().equals("dl")){
                traversingUrl(element);
            }

            if (element.tagName().equals("editor_h3")){
                //traversingUrl(element);
                Log.d(LOG_TAG, "folder: name:" + element.text() + " create time:" + element.attr("add_date") + " last_modify:" + element.attr("last_modified"));
            }

            if (element.tagName().equals("a")){
                Log.d(LOG_TAG, "folder: name:"+element.text() +" url:"+ element.attr("href")+" create time:"+ element.attr("add_date") + " last_modify:"+ element.attr("last_modified"));
            }

            // Log.d(LOG_TAG, "elements i :"+ i + " :" + elements.get(i).nodeName());
        }
    }

    public void testraversingFolders(){
        InputStream inputStream = getResources().openRawResource(R.raw.bookmarks_chrome);
        try {
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");
            Elements elements = doc.select("editor_h3");

            for (int i = 0; i < elements.size(); i++){
                Log.d(LOG_TAG, "elements i :"+ i + " :" + elements.get(i).select("editor_h3").size() + " name:"+elements.get(i).text());
                Element h3 = elements.get(i);
                Element dt = h3.parent().parent().parent();
                Log.d(LOG_TAG, "editor_h3:parent:"+h3.parent().tagName()+ " editor_h3:parent:parent:"+h3.parent().parent().tagName());
                if (dt.tagName().equals("dt")){
                    Element parentH3 = dt.select("editor_h3").first();
                    Log.d(LOG_TAG, "parent is:"+parentH3.text());
                }else{
                    Log.d(LOG_TAG, "this is root");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testChromeUrlsWithFolder(){
        InputStream inputStream = getResources().openRawResource(R.raw.bookmarks_chrome);

        try {
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");
            Elements elements = doc.select("editor_h3");

            for (int i = 0; i < elements.size(); i++){
                //Log.d(LOG_TAG, "elements i :"+ i + " :" + elements.get(i).select("editor_h3").size() + " name:"+elements.get(i).text());
                Element h3 = elements.get(i);
                Element dt = h3.parent().parent().parent();
               // Log.d(LOG_TAG, "editor_h3:parent:"+editor_h3.parent().tagName()+ " editor_h3:parent:parent:"+editor_h3.parent().parent().tagName());
                if (dt.tagName().equals("dt")){
                    Element parentH3 = dt.select("editor_h3").first();
                    //Log.d(LOG_TAG, "parent is:"+parentH3.text());
                }else{
                    //Log.d(LOG_TAG, "this is root");
                    continue;
                }

                //print links
                Element linkDl = h3.parent().child(1);
                if (linkDl.tagName().equals("dl")){
                    Elements linkDls = linkDl.children();
                    int index = 0;
                    for (int n = 0; n < linkDls.size(); n++){
                        Element linkElement = linkDls.get(n);

                        if (linkElement.tagName().equals("dt")){
                            if (linkElement.child(0).tagName().equals("a")){
                                printA(linkElement.child(0), h3, index);
                            }else if(linkElement.child(0).tagName().equals("editor_h3")){
                                printH3(linkElement.child(0), h3, index);
                            }
                            index++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printH3(Element h3, Element parentH3, int position){
        String message = "pos:"+position;
        if (h3 != null){
            message += " parent:"+h3.text();
        }

        message += "folder: name:" + h3.text() + " create time:" + h3.attr("add_date") + " last_modify:" + h3.attr("last_modified");
        Log.d(LOG_TAG, message);
    }

    private void printA(Element a, Element h3, int position ){
        String message = "pos:"+position;
        if (h3 != null){
            message += " parent:"+h3.text();
        }

        message += "website: name:"+a.text() +" url:"+ a.attr("href")+" create time:"+ a.attr("add_date") + " last_modify:"+ a.attr("last_modified");
        Log.d(LOG_TAG, message);
    }

    private void printElementInfo(Element element){
        Log.d(LOG_TAG, "nodesize:"+element.childNodeSize()+"");
        Log.d(LOG_TAG, "nodeName:"+element.nodeName());
        Log.d(LOG_TAG, "children size:" + element.children().size());
        Log.d(LOG_TAG, "slibingElements size:" + element.siblingElements().size());
        Log.d(LOG_TAG, "slibingNodes size:" + element.siblingNodes().size());
        Log.d(LOG_TAG, "slibingIndex:" + element.siblingIndex());
        Log.d(LOG_TAG, "elementSiblingIndex:" + element.elementSiblingIndex());
        Log.d(LOG_TAG, "slibingFirstElement:" + element.firstElementSibling().nodeName());
    }

}
