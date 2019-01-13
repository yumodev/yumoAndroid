package com.yumo.common.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yumodev on 5/6/16.
 */
public class YmIoUtil {

    /**
     * 将InputStream转换为字符串
     * @param is
     * @return
     * @throws IOException
     */
    public static String getStringFromInput(InputStream is) throws IOException {
        ByteArrayOutputStream byteOus = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        int size = 0;
        while ((size = is.read(tmp)) != -1) {
            byteOus.write(tmp, 0, size);
        }
        byteOus.flush();
        is.close();
        return byteOus.toString();
    }

    /**
     * 将InputStream转换为Bytes
     * @param in
     * @return
     */
    public static byte[] getBytesFromInput(InputStream in) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        out.write(buffer);
        return out.toByteArray();
    }

    public static void close(Closeable close){
        try {
            if (close != null){
                close.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
