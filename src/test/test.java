        package test;
        import javax.activation.DataHandler;
        import javax.activation.FileDataSource;
        import javax.mail.*;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeBodyPart;
        import javax.mail.internet.MimeMessage;
        import javax.mail.internet.MimeMultipart;
        import java.io.FileOutputStream;
        import java.util.Properties;

/**
 * Created by LENOVO on 2017/8/2.
 */
public class test {



    public static void main(String[] args) throws Exception {


        //邮箱的用户名  @163.com  之前的字符
         String username = "****";
        //邮箱的授权码
        String password = "授权码";
        //发送邮件的服务器地址
        String host = "smtp.163.com";


        Properties prop = new Properties();
        prop.setProperty("mail.host", host);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop);
        session.setDebug(true);
        Transport ts = session.getTransport();
//        ts.connect(host, username, password);
//        163邮箱connect方法，第三个参数应该是授权码，而不是密码.
        ts.connect(host, username, password);
        //创建邮件
        Message message = createEmail(session);
        //发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();

    }


    public static Message createEmail(Session session) throws Exception {

        //用于给用户发送邮件的邮箱
        String from = "17864307797@163.com";

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("收件人邮箱地址如  ***@163.com"));

        message.setSubject("用户注册邮件");

        String info = "Tanya Chua has composed songs for many heavyweight singers, including pop diva Faye Wong, Eason Chan, Na Ying and Stefanie Sun.\n" +
                " \n" +
                "In 1999, Chua signed a recording1 deal with PolyGram, which was acquired by Universal Music Group a year later.\n" +
                " \n" +
                "The company helped the singer release2 her first self-titled Chinese album.";
        message.setContent(info, "text/html;charset=UTF-8");
        message.saveChanges();
        return message;
    }

    //发送包含图片的邮件
    public static MimeMessage createImageMail(Session session) throws Exception {
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress("gacl@sohu.com"));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
        //邮件标题
        message.setSubject("带图片的邮件");

        // 准备邮件数据
        // 准备邮件正文数据
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这是一封邮件正文带图片<img src='cid:xxx.jpg'>的邮件", "text/html;charset=UTF-8");
        // 准备图片数据
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\1.jpg"));
        image.setDataHandler(dh);
        image.setContentID("xxx.jpg");
        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();
        //将创建好的邮件写入到E盘以文件的形式进行保存
        message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
        //返回创建好的邮件
        return message;
    }

    //发送包含附件的邮件
    public static MimeMessage createAttachMail(Session session) throws Exception {
        MimeMessage message = new MimeMessage(session);

        //设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress("gacl@sohu.com"));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
        //邮件标题
        message.setSubject("JavaMail邮件发送测试");

        //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("使用JavaMail创建的带附件的邮件", "text/html;charset=UTF-8");

        //创建邮件附件
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\2.jpg"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());  //

        //创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();
        //将创建的Email写入到E盘存储
        message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
        //返回生成的邮件
        return message;
    }
}