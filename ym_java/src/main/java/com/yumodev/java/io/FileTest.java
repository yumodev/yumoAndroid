package com.yumodev.java.io;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Created by yumodev on 9/21/16.
 */
public class FileTest {
    public static String TEST_DIR = "./testFile";

    /**
     * 打印一个目录的列表
     * @param dirName
     */
    public static void printList(String dirName){
        if (dirName == null || dirName.isEmpty()){
            return;
        }
        System.out.println("begin printList()");
        File path = new File(dirName);
        String[] list = path.list();
        for (String name : list){
            System.out.println(name);
        }
        System.out.println("end printList() \n\n\n");
    }

    /**
     * 打印一个目录的列表
     * @param dirName
     */
    public static void printList(String dirName, String regex){
        if (dirName == null || dirName.isEmpty()){
            return;
        }
        System.out.println("begin printList() "+regex);
        File path = new File(dirName);
        String[] list = path.list(new DirFilter(regex));
        for (String name : list){
            System.out.println(name);
        }
        System.out.println("end printList() "+regex+" \n\n\n");
    }

    static class DirFilter implements FilenameFilter{
        private Pattern mPattern;

        public DirFilter(String regex){
            mPattern = Pattern.compile(regex);
        }

        @Override
        public boolean accept(File dir, String name) {
            return mPattern.matcher(name).matches();
        }
    }

    public static boolean saveFileString(String fileName, String content){
        if (fileName == null || fileName.isEmpty()){
            return false;
        }

        boolean result = false;
        FileOutputStream ou = null;
        File file = new File(fileName);
        try {
            if (!file.exists()){
                file.createNewFile();
            }

            ou = new FileOutputStream(file);
            ou.write(content.getBytes());
            ou.flush();
            result = true;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            FileUtils.close(ou);
        }
        return result;
    }

    public static String readFileByByte(String fileName){
        if (fileName == null || fileName.isEmpty()){
            return "";
        }

        File file = new File(fileName);
        if (!file.exists()){
            return "";
        }

        String content = "";

        FileInputStream in = null;
        try {
            if (!file.exists()){
                file.createNewFile();
            }

            StringBuilder sb = new StringBuilder();
            in = new FileInputStream(file);
            int data;
            while ( (data = in.read()) != -1) {
                System.out.println(data);
                sb.append((char)data);
            }

            content = sb.toString();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            FileUtils.close(in);
        }

        return content;
    }

    public static String readFileByBytes(String fileName){
        if (fileName == null || fileName.isEmpty()){
            return "";
        }

        File file = new File(fileName);
        if (!file.exists()){
            return "";
        }

        String content = "";

        FileInputStream in = null;
        try {
            if (!file.exists()){
                file.createNewFile();
            }

            StringBuilder sb = new StringBuilder();
            in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer, 0, 2)) != -1) {
                System.out.println(new String(buffer, 0 , len));
                sb.append(new String(buffer, 0 , len));
            }

            content = sb.toString();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            FileUtils.close(in);
        }

        return content;
    }


    public static void main(String[] args){
        FileUtils.createDirectory(TEST_DIR);

        try {
            FileUtils.createFile(TEST_DIR+"/"+"1.txt");
            printList(TEST_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }

        printList(".");
        printList(".", ".*");
        FileUtils.printFileInfo(new File("./README.md"));
        FileUtils.printFileInfo(new File("."));
        String fileName = TEST_DIR+"/"+"2.txt";
        saveFileString(fileName, "112");
        FileUtils.printFileInfo(new File(fileName));
        System.out.println("begin read file by byte:"+fileName);
        System.out.println(readFileByByte(fileName));
        System.out.println("end read file by byte:"+fileName+"\n\n\n");

        System.out.println("begin read file by bytes:"+fileName);
        System.out.println(readFileByBytes(fileName));
        System.out.println("end read file by bytes:"+fileName+"\n\n\n");
    }
}
