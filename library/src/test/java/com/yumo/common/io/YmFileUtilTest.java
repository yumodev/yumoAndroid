package com.yumo.common.io;

import android.text.TextUtils;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.mockito.Matchers.any;


/**
 * Created by guo on 17/5/15.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class YmFileUtilTest extends TestCase {
    public final String LOG_TAG = "YmFileUtilTest";

    protected void setUp() throws Exception {
        super.setUp();
//        PowerMockito.mockStatic(TextUtils.class);
//        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
//            @Override
//            public Boolean answer(InvocationOnMock invocation) throws Throwable {
//                CharSequence a = (CharSequence) invocation.getArguments()[0];
//                return !(a != null && a.length() > 0);
//            }
//        });
    }

    public void testCreateFile(){
//        try {
//            YmFileUtil.createFile("test//1.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testSaveFile(){
        String content = "1234";
        //String fileName = "test"
    }
}
