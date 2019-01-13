package com.yumodev.process.test;

import android.content.Context;
import android.os.Build;

import com.yumo.common.io.YmFileUtil;
import com.yumo.common.log.LogFileUtil;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.process.util.MailUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;

/**
 * Created by yumo on 2018/6/13.
 */

public class TestMail extends YmTestFragment {

    public void testSendMail(){
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        threadPool.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                List<String> attachmentList = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                File logDir = new File(LogFileUtil.mDir);
                if (logDir.exists()){
                    String zipLogFile = logDir.getParent()+File.separator+"ymprocess.zip";
                    YmFileUtil.deleteFile(zipLogFile);
                    sb.append(YmFileUtil.fileToZip(logDir.getPath(), zipLogFile));
                    File file = new File(zipLogFile);
                    if (file.exists()){
                        attachmentList.add(zipLogFile);
                    }
                }else{
                    sb.append("不存在目录：");
                }

                boolean result = MailUtil.sendEmail("YmProcess日志", getContentMap(getContext()).toString(), attachmentList);
                showToastMessageOnUiThread(result+"");
                return true;
            }
        });
    }

    private static Map<String,String> getContentMap(Context context){
        Map<String, String> contentMap = new HashMap<>();

        contentMap.put("Brand", Build.MANUFACTURER.toLowerCase());
        contentMap.put("Version", Build.VERSION.SDK_INT + "");
        contentMap.put("Model", Build.MODEL);
        return contentMap;
    }

}
