package com.yumodev.java.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by yumodev on 9/21/16.
 */
public class FileUtils {
    public static void printFileInfo(File file){
        if (!file.exists()){
            System.out.println("file  not exist:"+file.getName());
            return;
        }

        System.out.println("begin printFileInfo "+file.getName());
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("getName():"+file.getName()+"\n");
            sb.append("getAbsolutePath():"+file.getAbsolutePath()+"\n");
            sb.append("length:"+file.length()+"\n");
            sb.append("getParent():"+file.getParent()+"\n");
            sb.append("getPath():"+file.getPath()+"\n");
            sb.append("getFreeSpacePath():"+file.getFreeSpace()+"\n");
            sb.append("getTotalSpace():"+file.getTotalSpace()+"\n");
            sb.append("canExecute():"+file.canExecute()+"\n");
            sb.append("canRead():"+file.canRead()+"\n");
            sb.append("canWrite:"+file.canWrite()+"\n");
            sb.append("lastModified():"+file.lastModified()+"\n");
            sb.append("isAbsolute:"+file.isAbsolute()+"\n");
            sb.append("isDirectory:"+file.isDirectory()+"\n");
            sb.append("isFile():"+file.isFile()+"\n");
            sb.append("isHidden():"+file.isHidden()+"\n");


            sb.append("getCanonicalPath():"+file.getCanonicalPath()+"\n");
        }catch (IOException e){
            e.printStackTrace();
        }


        System.out.println(sb.toString());
        System.out.println("end printFileInfo "+file.getName()+"\n\n\n");
    }


    public static boolean createDirectory(String fileName){
        if (fileName == null || fileName.isEmpty()){
            return false;
        }

        File file = new File(fileName);
        if (file.exists()){
            return true;
        }

        return file.mkdir();
    }


    public static boolean createFile(String fileName) throws IOException{
        if (fileName == null || fileName.isEmpty()){
            return false;
        }

        File file = new File(fileName);
        if (file.exists()){
            return true;
        }

        return file.createNewFile();
    }

    public static void close(Closeable close){
        if (close != null){
            try {
                close.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
