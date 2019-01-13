/**
 * YmFileUtil.java
 * yumodev
 * 2014-12-3
 */
package com.yumo.common.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.text.TextUtils;

import com.yumo.common.define.YmDefine;
import com.yumo.common.log.Log;

import static com.yumo.common.io.YmCloseUtil.close;

public class YmFileUtil {
    public static final String LOG_TAG = "YmFileUtil";
    /**
     * 检查文件是否存在，不存在则创建该文件
     * @param fileName
     * @return
     */
    public static boolean isExistFile(String fileName){
        if (TextUtils.isEmpty(fileName)){
            return false;
        }

        File file = new File(fileName);
        if (file.exists() && file.isFile()){
            return true;
        }

        return false;
    }

    /**
     * 目录是否存在
     * @param directory
     * @return
     */
    public static boolean isExistDirectory(String directory) {
        if (TextUtils.isEmpty(directory)){
            return false;
        }

        boolean result = false;
        File file = new File(directory);
        if (file.exists() && file.isDirectory()) {
            result = true;
        }
        return result;
    }

    /**
     * 创建文件。文件已存在，不在重新创建。
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean createFile(String fileName) throws IOException{
        if (TextUtils.isEmpty(fileName)){
            return false;
        }
        return createFile(fileName, false);
    }

    /**
     * 创建文件
     * @param fileName
     * @param forceCreate 如果文件存在是否先删除在创建。
     * @return
     * @throws IOException
     */
    public static boolean createFile(String fileName, boolean forceCreate) throws IOException{
        if (TextUtils.isEmpty(fileName)){
            return false;
        }
        return createFile(new File(fileName), forceCreate);
    }

    /**
     * 创建文件
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean createFile(File file) throws IOException{
        if (file == null){
            return false;
        }
        return createFile(file, false);
    }

    /**
     * 创建文件。
     * @param file
     * @param forceCreate
     * @return
     * @throws IOException
     */
    public static boolean createFile(File file, boolean forceCreate) throws IOException{
        if (file == null){
            return false;
        }

        if (file.exists()){
            if (!forceCreate){
                return false;
            }else{
                file.delete();
            }
        }

        if (!isExistDirectory(file.getParentFile().getAbsolutePath())){
            file.getParentFile().mkdirs();
        }

        return file.createNewFile();
    }

    /**
     * 创建目录。
     * @param dir
     * @return true：已存在或者创建成功。
     */
    public static boolean createDirectory(String dir) {
        if (TextUtils.isEmpty(dir)){
            return false;
        }

        boolean result = true;
        File file = new File(dir);

        if (!file.exists()) {
            result = file.mkdirs();
        }
        return result;
    }


    /**
     * 保存一个字符串到文件中。
     * @param fileName
     * @param content
     * @return
     */
    public static boolean saveFile(String fileName, String content){
        if (content == null){
            return false;
        }

        if (TextUtils.isEmpty(fileName)){
            return false;
        }

        return saveFile(fileName, content.getBytes());
    }

    public static boolean saveFile(String fileName, byte[] bytes){
        return saveFile(fileName, bytes, false);
    }

    /**
     * 保存Bytes 到一个文件
     * @param fileName
     * @param bytes
     * @return
     */
    public static boolean saveFile(String fileName, byte[] bytes, boolean append) {
        if (bytes == null){
            return false;
        }

        if (TextUtils.isEmpty(fileName)){
            return false;
        }

        FileOutputStream out = null;
        try {
            if (append){
                if (!isExistFile(fileName)){
                    createFile(fileName);
                }
            }else {
                if (!createFile(fileName, true)){
                    return false;
                }
            }

            out = new FileOutputStream(fileName, append);
            out.write(bytes);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            YmIoUtil.close(out);
        }

        return false;
    }

    /**
     * 保存Input 到一个文件
     * @param fileName
     * @param in
     * @return
     */
    public static boolean saveFile(String fileName, InputStream in) {
        if (TextUtils.isEmpty(fileName) || in == null ){
            return false;
        }

        boolean flag = false;
        FileOutputStream out = null;
        try {
            if (!createFile(fileName, true)){
                return false;
            }

            out = new FileOutputStream(fileName);
            byte[] buf = new byte[4096];
            int len = 0;
            while((len = in.read(buf)) != -1){
                out.write(buf, 0, len);
            }
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            YmIoUtil.close(in);
            YmIoUtil.close(out);
        }

        return flag;
    }

    /**
     * 读取一个文件。
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readToString(String fileName) throws IOException{
        if (TextUtils.isEmpty(fileName)){
            return null;
        }

        if (!isExistFile(fileName)){
            return null;
        }


        return String.valueOf(readToBytes(fileName));
    }

    /**
     * 读取一个文件。
     * @param fileName
     * @return
     * @throws IOException
     */
    public static byte[] readToBytes(String fileName){
        if (TextUtils.isEmpty(fileName)){
            return null;
        }

        if (!isExistFile(fileName)){
            return null;
        }

        FileInputStream in = null;
        ByteArrayOutputStream byteOus = null;

        try {
            in = new FileInputStream(fileName);
            byteOus = new ByteArrayOutputStream();
            byte[] tmp = new byte[1024];
            int size = 0;
            while ((size = in.read(tmp)) != -1) {
                byteOus.write(tmp, 0, size);
            }
            byteOus.flush();
            return byteOus.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            YmIoUtil.close(in);
            YmIoUtil.close(byteOus);
        }

        return null;
    }

    /**
     * 删除一个文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(final String fileName) {
        if (TextUtils.isEmpty(fileName)){
            return false;
        }

        boolean result = false;
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            result = file.delete();
        }
        return result;
    }


    /**
     * 递归删除目录
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 打印目录
     * @param path
     */
    public static void printDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                Log.i(LOG_TAG, "dir empty!"+path);
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        Log.i(LOG_TAG, "dir:" + file2.getAbsolutePath());
                        printDir(file2.getAbsolutePath());
                    } else {
                        Log.i(LOG_TAG, "file:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            Log.i(LOG_TAG, "dir no exist!");
        }
    }

    /**
     * 重命名文件，如果新文件已存在，返回false
     * @param oldName
     * @param newName
     * @oaram delNewFile 如果新文件已经存在是否删除删除
     * @return
     */
    public static boolean rename(String oldName, String newName, boolean delNewFile){
        File oldFile = new File(oldName);
        File newFile = new File(newName);

        if (!oldFile.exists()){
            return false;
        }

        boolean result = true;
        if (newFile.exists()){
            if (delNewFile){
                result = deleteFile(newName);
            }
        }

        if(result && oldFile.renameTo(newFile)){
            return true;
        }
        return false;
    }

    /**
     * 通过一个文件的完整路径获取其文件名字。
     * 本函数未对传递的路径进行合法性检测。
     * @param path 文件路径
     * @return: String 返回文件名字。
     */
    public static String getFileNameFromPath(final String path) {
        if (TextUtils.isEmpty(path)){
            return path;
        }
        String[] arr = path.split(File.separator);
        return arr.length == 0 ? "" : arr[arr.length - 1];
    }

    /**
     * 通过一个文件的完整路径获取文件不含扩展名的文件名.
     * 本函数未对传递的路径进行合法性检测
     * @param path 文件路径
     * @return: String 返回不含扩展名的文件名
     */
    public static String getFileNameNoTensionByPath(final String path) {
        String fileName = getFileNameFromPath(path);
        if (TextUtils.isEmpty(fileName)){
            return fileName;
        }

        int nFind = fileName.indexOf('.');
        if (nFind > 0) {
            return fileName.substring(0, nFind);
        }
        return "";
    }

    /**
     * 返回扩展名
     * @param fileName
     * @return
     */
    public static String getFileExtension(final String fileName){
        if (TextUtils.isEmpty(fileName)){
            return "";
        }
        int nFind = fileName.lastIndexOf('.');
        if (nFind >= 0) {
            return fileName.substring(nFind);
        }
        return "";
    }


    /**
     * 复制文件。默认会覆盖
     * @param source
     * @param target
     */
    public static boolean copyFile(String source, String target){
        return copyFile(new File(source), new File(target));
    }

    public static boolean copyFile(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(inStream);
            close(in);
            close(outStream);
            close(out);
        }
        return false;
    }


    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     * @param sourceFilePath :待压缩的文件路径
     * @param zipFileName :压缩后存放路径
     * @return
     */
    public static int fileToZip(String sourceFilePath,String zipFileName){
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if(sourceFile.exists() == false){
            return YmDefine.ERROR_FILE_IO_ERROR;
        }else{
            try {
                File zipFile = new File(zipFileName);
                if(zipFile.exists()){
                    return YmDefine.ERROR_FILE_EXISTS;
                }else{
                    File[] sourceFiles = sourceFile.listFiles();
                    if(null == sourceFiles || sourceFiles.length<1){
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    }else{
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024*10];
                        for(int i=0;i<sourceFiles.length;i++){
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024*10);
                            int read = 0;
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){
                                zos.write(bufs,0,read);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return YmDefine.ERROR_FILE_IO_ERROR;
            } catch (IOException e) {
                e.printStackTrace();
                return YmDefine.ERROR_FILE_IO_ERROR;
            } finally{
                YmIoUtil.close(bis);
                YmIoUtil.close(zos);
            }
        }
        return YmDefine.FILE_SUCCESS;
    }
}
