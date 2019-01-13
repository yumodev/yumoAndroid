package com.yumodev.java.io;

import junit.framework.TestCase;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yumodev on 17/11/16.
 */

public class FileTestCase extends TestCase {

    /**
     * 打印当前目录信息下的文件和目录名称
     */
    public void testCurrentDirListName(){
        File path = new File(".");
        String[] list = path.list();
        for (String name : list){
            System.out.println(name);
        }
    }

    /**
     * 打印当前目录下的文件和目录的路径。
     */
    public void testCurrentDirListPaht(){
        File path = new File(".");
        File[] list = path.listFiles();
        for (File file : list){
            System.out.println(file.getAbsolutePath());
        }
    }

    /**
     * 利用FileNameFilter过滤目录列表
     */
    public void testFileNameFilter(){
        File path = new File(".");
        File[] list = path.listFiles((dir, name) -> {
            if (name.contains(".md")){
                return true;
            }
            return false;
        });

        for (File file : list){
            System.out.println(file.getAbsolutePath());
        }
    }

    /**
     * 利用FileNameFilter和正则表达式过滤目录列表
     */
    public void testFileNameFilter1(){
        File path = new File(".");
        File[] list = path.listFiles((dir, name) -> {
            String regImg = String.format("^%s.*", "ym");
            Pattern pattern = Pattern.compile(regImg);
            Matcher matcher = pattern.matcher(name);
            return matcher.find();
        });

        for (File file : list){
            System.out.println(file.getAbsolutePath());
        }

    }
}
