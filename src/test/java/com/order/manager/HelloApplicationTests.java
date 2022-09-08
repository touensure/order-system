package com.order.manager;

import com.order.manager.service.Sender;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HelloApplicationTests {

    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        for(int i = 0; i <=10; i++) {
            sender.sendInDefaultExchange(i);
        }
    }

    @Test
    public void testTopicExchange(){
        sender.sendInTopicExchange();
    }
    @Test
    public void testFanoutExchange(){
        sender.sendInfanoutExchange();
    }

    @Test
    public void testSendingEmail() throws Exception{
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址xxx
        message.setFrom(new InternetAddress("1319654019@qq.com"));
        // 设置多个收件人邮箱地址或一个收件人
        // message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("1319654019@qq.com"));//一个收件人
        // 设置邮件标题
        message.setSubject("标题");
        // 设置邮件内容
        message.setText("这是一个QQ邮件");
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户，QQ邮箱授权码在QQ邮箱中激活使用
        transport.connect("1319654019@qq.com", "svoomqmsagxxhbie");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    @Test
    public void testSendingEmail_by_() throws Exception{
        //邮箱服务器地址如：mail.qq.com
        String hostName ="smtp.qq.com";
        // 帐号与密码
        String userName ="1319654019@qq.com";
        String password = "svoomqmsagxxhbie";
        // 发件人
        String fromAddress ="1319654019@qq.com";
        // 发件人姓名
        String fromName = "pillarzhang";
        HtmlEmail email=new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setAuthentication("1319654019@qq.com", "svoomqmsagxxhbie");
        email.setCharset("utf-8");//非必需
        email.setFrom("1319654019@qq.com", "Customer Services Team", "utf-8");//必须且from eamil需要和对应server匹配
        email.setSubject("testMail");// 非必需
        email.setHtmlMsg("这是一个QQ邮件");//必须javax.mail.MessagingException: IOException while sending message;nested exception is:java.io.IOException: Exception writing Multipart
        email.addTo("1319654019@qq.com", "pillarzhang", "utf-8");//必须
        email.setSmtpPort(587);//必须或者465
        email.setTLS(false);//非必需
        email.setSSLOnConnect(true);//必须Caused by: javax.mail.MessagingException: Exception reading response;nested exception is:java.net.SocketTimeoutException: Read timed out
        System.err.println(email.getSmtpPort());
        System.err.println(email.getHostName());
        String res = email.send();// 发送邮件
    }

    @Test
    public void testSendingEmail_by_Amazon_server() throws Exception{
        //邮箱服务器地址如：mail.qq.com
        String hostName ="email-smtp.us-east-1.amazonaws.com";
        // 帐号与密码
        String userName ="AKIAZUC6MYKDIL6ZN5RP";
        String password = "BIt+ddG7U2sTEvZ4zDAyTQMn/KdqKe1Bq9PoC31hLMgA";
        // 发件人
        String fromAddress ="welcome@upscalecommerce.com";
        // 发件人姓名
        String fromName = "pillarzhang";
        HtmlEmail email=new HtmlEmail();
        email.setHostName(hostName);// 设置smtp服务器
        email.setAuthentication(userName, password);// 设置授权信息
        email.setCharset("utf-8");
        email.setFrom(fromAddress, fromName, "utf-8");// 设置发件人信息
        email.setSubject("testMail");// 设置主题
        email.setHtmlMsg("这是一个QQ邮件");// 设置邮件内容
        email.addTo("1319654019@qq.com", "pillarzhang", "utf-8");
        email.setSmtpPort(587);
//        email.setStartTLSEnabled(false);
        email.setSSLOnConnect(true);
        System.err.println(email.getSmtpPort());
        System.err.println(email.getHostName());
        String res = email.send();// 发送邮件
    }

    @Test//打印inputstream到控制台
    public void testSendingEmail_by_Amazon_server(InputStream inputStream) throws Exception{
//        InputStreamReader isr =new InputStreamReader(is,"utf-8");
        InputStreamReader isr =new InputStreamReader(inputStream);
        BufferedReader br =new BufferedReader(isr);
        while ((br.read())!=-1) {
            System.out.println(br.readLine());
        }
    }
}
