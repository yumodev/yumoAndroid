package com.yumodev.java.regular;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yumodev on 17/9/8.
 */

public class TestRegular extends TestCase{

    public void test(){
        String str = "[\"60335\",\"\\u5185\\u8499\\u53e4\",\"beijing\",\"\\u5174\\u5b89\\u76df\",\"xinganmeng\",\"\\u7a81\\u6cc9\",\"tuquan\"]";
        String str1 = "[\"603356\",\"\\u5185\\u8499\\u53e4\",\"beijing\",\"\\u5174\\u5b89\\u76df\",\"xinganmeng\",\"\\u7a81\\u6cc9\",\"tuquan\"]";

        str += str1;
        //String regImg= String.format("\\[\\\"(\\d*)\\\".*?\\\"(.u7a81.u6cc9)\\\".*?\\]");
        String regImg = String.format("\\[\"(\\d*)\"[^\\[]*\"%s\",\"(\\w*)\"\\]", ".u7a81.u6cc9");

        String value = "", value1="";
        Pattern pattern = Pattern.compile(regImg);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));

            value = matcher.group(1);
            value1 = matcher.group(2);
        }
        System.out.println("test:"+value+" "+value1);
    }

    public void test1(){
       assertTrue(validString("ddd.dd"));
    }
    /**
     * 检测用户id，是否合法。
     * @param cs
     * @return
     */
    public boolean validString(CharSequence cs){
        String regEx = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(cs.toString());
        return !m.find();
    }
}
