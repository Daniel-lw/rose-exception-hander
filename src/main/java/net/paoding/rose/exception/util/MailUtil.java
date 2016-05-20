package net.paoding.rose.exception.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

public class MailUtil {


    /**
     * 发送邮件
     * 邮箱账号默认
     *
     * @throws UnsupportedEncodingException
     */

    protected static void sendMail(String title, String content, List<String> tos) throws UnsupportedEncodingException {


        String host = "邮箱主机";
        String username = "账号";
        String password = "密码";
        String from = "from名称";
        int port = 25;

        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", username);
        // props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "true");

        try {
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));


            for (String to : tos) {
                InternetAddress to_address = new InternetAddress(to);
                message.addRecipient(Message.RecipientType.TO, to_address);
            }

            message.setSubject(title, "UTF-8");

            String nick = "";
            try {
                nick = javax.mail.internet.MimeUtility.encodeText("监控系统", "utf-8", null);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            message.setFrom(new InternetAddress(nick + " <" + from + ">"));

            Multipart multipart = new MimeMultipart();

            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);

            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            Long sendStart = System.currentTimeMillis();
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("mail send cost:" + (System.currentTimeMillis() - sendStart) + "-->success");
            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
