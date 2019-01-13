package com.yumo.android.test.java;

import com.yumo.demo.view.YmTestFragment;

import java.io.UnsupportedEncodingException;

/**
 * Created by yumo on 6/13/16.
 * 字符串测试类
 */
public class TestString extends YmTestFragment {

    public void testStringLength(){
        StringBuilder builder = new StringBuilder();
        builder.append("abc:"+ (new String("abc")).length());
        builder.append("/n123:"+ (new String("123")).length());
        builder.append("/n我的天:"+ (new String("123")).length());

        showToastMessage(builder.toString());
    }

    public void testStringBytes(){
        StringBuilder builder = new StringBuilder();
        builder.append("abc:"+ (new String("abc")).getBytes().length);
        builder.append("/n123:"+ (new String("123")).getBytes().length);
        builder.append("/n我的天:"+ (new String("我的天")).getBytes().length);

        showToastMessage(builder.toString());
    }

    public void testStringWorkCount(){
        StringBuilder builder = new StringBuilder();
        builder.append("abc:"+ getByteLength("abc"));
        builder.append("/n123:"+ getByteLength("123"));
        builder.append("/n我的天:"+ getByteLength("我的天123，."));

        showToastMessage(builder.toString());
    }

    public void testSubString(){
        String str = "我1的天";
        str = subStringByte(str, 3);

        showToastMessage(str);
    }

    public int getByteLength(String s){
        int length = 0;

        for(int i = 0; i < s.length(); i++){
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255){
                length ++;
            }else{
                length += 2;
            }
        }

        return length;
    }

    public String subStringByte(String str, int count){
        if (getByteLength(str) <= count){
            return  str;
        }

        StringBuilder builder = new StringBuilder();
        int length = 0;
        for(int i = 0; i < str.length(); i++){
            int ascii = Character.codePointAt(str, i);
            if(ascii >= 0 && ascii <=255){
                length ++;
            }else{
                length += 2;
            }

            if (length <= count){
                builder.append(str.charAt(i));
            }else{
                break;
            }
        }

        return builder.toString();
    }
    /**
     * 判断是否是一个中文汉字
     *
     * @param c  字符
     * @return true表示是中文汉字，false表示是英文字母
     * @throws UnsupportedEncodingException
     *             使用了JAVA不支持的编码格式
     */
    public static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("GBK").length > 1;
    }

    /**
     * 按字节截取字符串
     *
     * @param orignal
     *            原始字符串
     * @param count
     *            截取位数
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException
     *             使用了JAVA不支持的编码格式
     */
    public static String substring(String orignal, int count)
            throws UnsupportedEncodingException {
        // 原始字符不为null，也不是空字符串
        if (orignal != null && !"".equals(orignal)) {
            // 将原始字符串转换为GBK编码格式
            orignal = new String(orignal.getBytes(), "GBK");
            // 要截取的字节数大于0，且小于原始字符串的字节数
            if (count > 0 && count < orignal.getBytes("GBK").length) {
                StringBuffer buff = new StringBuffer();
                char c;
                for (int i = 0; i < count; i++) {
                    // charAt(int index)也是按照字符来分解字符串的
                    c = orignal.charAt(i);
                    buff.append(c);
                    if (isChineseChar(c)) {
                        --count;
                    }
                }
                return buff.toString();
            }
        }
        return orignal;
    }

}
