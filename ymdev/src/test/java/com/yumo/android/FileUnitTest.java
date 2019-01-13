package com.yumo.android;

import org.junit.Test;

import java.io.File;

/**
 * Created by yumodev on 17/4/20.
 */

public class FileUnitTest {

    @Test
    public void testFileAttr(){
        String fileName = "/etc/test.txt";
        File file = new File(fileName);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParentFile().getAbsolutePath());
    }
}
