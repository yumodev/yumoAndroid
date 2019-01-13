package com.yumo.android.test.sys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.io.YmSdUtil;
import com.yumo.common.log.Log;
import com.yumo.common.log.LogFileUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by yumo on 2018/5/14.
 * <p>
 * https://code.google.com/archive/p/javamail-android/downloads
 * https://blog.csdn.net/aowoWolf/article/details/53432561
 */

public class EmailTestView extends YmTestFragment {

    private final String LOG_TAG = "EmailTestView";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate", true);
    }

    public void testSendTo() {
        Intent mail = new Intent(Intent.ACTION_SENDTO);
        mail.setData(Uri.parse("mailto:yumo@icongtai.com"));
        mail.putExtra(Intent.EXTRA_SUBJECT, "测试发送邮件");
        mail.putExtra(Intent.EXTRA_TEXT, "测试邮件内容");
        startActivity(mail);
    }


    public void testSendMain() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendTextMail();

            }
        }).start();
    }

    /**
     * 以文本格式发送邮件
     */
    public boolean sendTextMail() {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = new Properties();
        pro.put("mail.smtp.host", "smtp.163.com");
        pro.put("mail.smtp.port", "25");
        pro.put("mail.smtp.auth", "true");

        if (true) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator("disheng54@163.com", "yumo00");
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress("disheng54@163.com");
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress("yumo@icongtai.com");
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject("主题");
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = "内容";
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public void testSendFile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String fileName = LogFileUtil.getLogFileName(LOG_TAG);
                List<String> fileList = Arrays.asList(fileName);
                sendEmail("测试邮件", "hello", "", "发送内容", fileList);

            }
        }).start();

    }

    private void sendEmail(String subject, String recepits, String sender, String content, List<String> fileList) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");  //设置smtp的服务器地址
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");

        // 如果需要身份认证，则创建一个密码验证器
        MyAuthenticator auth = new MyAuthenticator("disheng54@163.com", "yumo00");
        // 获取会话对象
        Session session = Session.getInstance(props, auth);
        // 设置为DEBUG模式
        session.setDebug(true);

        // 邮件内容对象组装
        MimeMessage message = new MimeMessage(session);
        try {
            Address addressFrom = new InternetAddress("disheng54@163.com");
            Address addressTo = new InternetAddress("duzi_tingyu@163.com");
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setFrom(addressFrom);
            message.addRecipient(Message.RecipientType.TO, addressTo);

            // 邮件文本/HTML内容
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/plain");
            multipart.addBodyPart(messageBodyPart);

            // 添加邮件附件
            for (String filePath : fileList) {
                MimeBodyPart attachPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filePath);
                attachPart.setDataHandler(new DataHandler(source));
                attachPart.setFileName(filePath);
                multipart.addBodyPart(attachPart);
            }


            // 保存邮件内容
            message.setContent(multipart);

//            // 获取SMTP协议客户端对象，连接到指定SMPT服务器
//            Transport transport = session.getTransport("smtp");
//            transport.connect(host, Integer.parseInt(port), userName, password);
//            System.out.println("connet it success!!!!");

            // 发送邮件到SMTP服务器
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            Transport.send(message);
            System.out.println("send it success!!!!");

            // 关闭连接
//            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testSendZipFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String logDir = LogFileUtil.createLogRootDir();
                File file = new File(logDir);
                String parentFile = file.getParent();
                String zipFileName = parentFile + File.separator+"log.zip";
                YmFileUtil.deleteFile(zipFileName);

                YmFileUtil.fileToZip(LogFileUtil.createLogRootDir(), zipFileName);

                List<String> fileList = Arrays.asList(zipFileName);
                sendEmail("测试邮件", "hello", "", "发送内容", fileList);

            }
        }).start();
    }




    public class MyAuthenticator extends Authenticator {
        String userName = null;
        String password = null;

        public MyAuthenticator() {
        }

        public MyAuthenticator(String username, String password) {
            this.userName = username;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }
}
