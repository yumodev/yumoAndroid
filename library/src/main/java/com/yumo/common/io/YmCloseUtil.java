package com.yumo.common.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by yumodev on 17/2/23.
 */

public class YmCloseUtil {
    /**
     * 关闭Closeable接口
     * @param close
     */
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
