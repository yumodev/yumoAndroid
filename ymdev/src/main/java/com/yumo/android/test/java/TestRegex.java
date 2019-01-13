package com.yumo.android.test.java;

import android.util.Log;

import com.yumo.common.io.YmIoUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yumo on 5/5/16.
 * 测试正则表达式
 */
public class TestRegex extends YmTestFragment {
    private final String LOG_TAG = "TestRegex";

    /**
     * 查询匹配的字符串
     */
    public void testMatchFind(){
        // 要验证的字符串
        String str = "my name is mzd";
        // 正则表达式规则
        String regEx = "name.*";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        // 忽略大小写的写法 //
        Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        boolean rs = matcher.find();
        System.out.println(rs);

    }

    /**
     * ^ 行的开始， $行的结束
     */
    public void testMatchBeginEnd(){
        // 要验证的字符串
        String str = "my";
        // 正则表达式规则
        String regEx = "^my$";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        // 忽略大小写的写法 //
        Matcher matcher = pattern.matcher(str);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        boolean rs = matcher.find();
        if (rs){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            Log.d(LOG_TAG,matcher.group());
        }
        System.out.println(rs);
    }

    /**
     * [] 匹配字符
     * - 连字符 1-6 标识匹配1-6之间的数字
     * ^ 不匹配 ^1-6 标识不匹配1-6之间的元素
     * . 匹配任意字符，但是不能这样使用[.]
     */
    public void testMatchBracket(){
        String str = "my yumo yUmo, y1mo";
        //String regEx = "y[uU]mo";
        //String regEx = "y[a-zA-Z]mo";
        //String regEx = "y[^U]mo";
        String regEx = "y.mo";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
        }else{
            Log.d(LOG_TAG, "not find");
        }
    }

    /**
     * (|) 标识或的意思
     */
    public void testMatchOr(){
        String str = "my yumo yUmo, y1mo";

        String regEx = "y(u|U|[1-9])mo";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
        }else{
            Log.d(LOG_TAG, "not find");
        }
    }


    /**
     * 单词分界符
     */
    public void testWordBoundDaries(){
        String str = "my yumo yUmo, y1mo";
        //String regEx = "y\\w+";
        String regEx = "y\\w+o";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
        }else{
            Log.d(LOG_TAG, "not find");
        }
    }


    /**
     * 打印html标签
     */
    public void testPrintHtmlTag(){
        String str = "";
        try {
            str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_note.html"));
            //str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_image.html"));
            str = "<p>strip_tags</p> <p>　　去掉 HTML 及 PHP 的标记。</p> <p>　　语法: string strip_tags(string str);</p>";
        } catch (IOException e) {
            e.printStackTrace();
        }
        String regEx = "<[^>]+>";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
        }else{
            Log.d(LOG_TAG, "not find");
        }
    }


    /**
     * 打印html标签
     */
    public void testReplaseHtmlTag(){
        String str = "";
        try {
            str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_note.html"));
            //str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_image.html"));
            str = "<p>strip_tags</p> <p>　　去掉 HTML 及 PHP 的标记。</p> <p>　　语法: string strip_tags(string str);</p>";
        } catch (IOException e) {
            e.printStackTrace();
        }
        String regEx = "<[^>]+>";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
            str = matcher.replaceAll("");
        }else{
            Log.d(LOG_TAG, "not find");
        }
        Log.d(LOG_TAG, str);
    }

    /**
     * 打印img标签
     */
    public void testPrintImageTag(){
        String str = "";
        try {
            str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_image.html"));
            //str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_image.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String regEx = "<img[^>]*>";
        String regEx = "<img[^>]+>";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
        }else{
            Log.d(LOG_TAG, "not find");
        }
    }

    /**
     * 打印img src 网址
     */
    public void testPrintImagehasSrc(){
        String str = "";
        try {
           // str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_note.html"));
            str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_image.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String regEx = "<img[^<>]+src=([\\\"'])([^\\\"']*)\\\\1[^<>]*>";
        String regEx = "<img[^>]+src\\s*=\\s*[^>]+>";

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group());
            }while (matcher.find());
        }else{
            Log.d(LOG_TAG, "not find");
        }
    }


    /**
     * 打印img src 网址
     */
    public void testPrintImageSrc(){
        String str = "";
        try {
            // str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_note.html"));
            str = YmIoUtil.getStringFromInput(getContext().getAssets().open("test_image.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String regEx = "<img[^<>]+src=([\\\"'])([^\\\"']*)\\\\1[^<>]*>";
        //String regImg= "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]+>";
        String regImg= "<img[^>]+src\\s*=\\s*(['\"])([^'\"]*)\\1[^>]*>";
        String regSrc = "src\\s*=\\s*(['\"])([^'\"]+)['\"]";
        String regImgUrl = "['\"]([^'\"]+)['\"]";


        Pattern pattern = Pattern.compile(regImg, Pattern.CASE_INSENSITIVE);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            String img = matcher.group(2);
            Log.d(LOG_TAG, "img:"+img);

//            Matcher srcMatcher = Pattern.compile(regSrc).matcher(img);
//            while (srcMatcher.find()){
//                String src = srcMatcher.group();
//                Log.d(LOG_TAG, "src :"+src);
//                Matcher imgUrlMatcher = Pattern.compile(regImgUrl).matcher(src);
//                while (imgUrlMatcher.find()){
//                    String imgUrl = imgUrlMatcher.group();
//                    imgUrl = imgUrl.substring(1,imgUrl.length()-1);
//                    Log.d(LOG_TAG, "imgUrl:"+imgUrl);
//                }
//            }
        }
    }


    /**
     * 打印A标签
     */
    public void testPrintATag(){
        String str = "<a id=\"origin\" href=\"http://baidu.com\">dddd</a>";
        String regEx = String.format("<a[^>]*id\\s*=\\s*(['\"])%s\\1[^>]*>([^<]*)</a>","origin");

        Pattern pattern = Pattern.compile(regEx);
        Log.d(LOG_TAG, "testMatchFind: pattern:"+pattern.pattern());
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            Log.d(LOG_TAG, "groupNum:"+matcher.groupCount());
            do{
                Log.d(LOG_TAG,matcher.group(2));
            }while (matcher.find());
        }else{
            Log.d(LOG_TAG, "not find");
        }
    }

}
