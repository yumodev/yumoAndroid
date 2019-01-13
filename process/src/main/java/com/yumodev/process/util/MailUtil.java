package com.yumodev.process.util;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by yumo on 2018/5/15.
 * 发送邮件的工具类，
 */

public class MailUtil {



    public static boolean sendEmail(String subject, String content, List<String> fileList){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");  //设置smtp的服务器地址
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");

        // 如果需要身份认证，则创建一个密码验证器
        MyAuthenticator auth = new MyAuthenticator("disheng54@163.com", "yumo00");
        // 获取会话对象
        Session session = Session.getInstance(props, auth);
        // 设置为DEBUG模式
        session.setDebug(true);
        try {
            // 邮件内容对象组装
            MimeMessage message = new MimeMessage(session);

            Address addressFrom = new InternetAddress("disheng54@163.com");
            Address addressTo = new InternetAddress("yumo@icongtai.com");
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setFrom(addressFrom);
            message.addRecipient(Message.RecipientType.TO, addressTo);

            // 邮件文本/HTML内容
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/plain;charset=UTF-8");
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
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static class MyAuthenticator extends Authenticator {
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
