package mail;

import domain.User;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class mailutil {
    public static void send(User user) throws MessagingException, GeneralSecurityException {
        //创建一个配置文件并保存
        Properties properties = new Properties();
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("填写你的qq邮箱","填写你的秘钥");
            }
        });
        //开启debug模式
        session.setDebug(false);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器
        transport.connect("smtp.qq.com","填写你的qq邮箱","16位授权码");
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("填写你的qq邮箱"));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
        //邮件标题
        mimeMessage.setSubject("智能问答机器人");
        //邮件内容
        mimeMessage.setContent("您的密码为："+user.getPassword(),"text/html;charset=UTF-8");
        //发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        //关闭连接
        transport.close();
    }
    public static boolean receive(User user) {
        boolean flag=false;
        String pop3Server = "pop.qq.com";
        String protocol = "pop3";
        String username = "填写你的qq邮箱";
        String password = "填写你的秘钥";
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.pop3.host", pop3Server);
        props.setProperty("mail.pop3.ssl.enable", "true");
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.auth", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        username, password);
            }

        });
        session.setDebug(false);
        Store store = null;
        try {
            store = session.getStore(protocol);
            store.connect(pop3Server, username, password);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        Folder folder = null;
        try {
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();
            flag=parseMessage(user,messages);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (folder != null) {
                    folder.close(true);
                }
                if (store != null) {
                    store.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    private static boolean parseMessage(User user,Message ...messages) throws MessagingException, IOException {
        boolean flag=false;
        if (messages == null || messages.length < 1)
            throw new MessagingException("未找到要解析的邮件!");
        // 解析所有邮件
        for(int i=0;i<messages.length;++i) {
            MimeMessage msg = (MimeMessage) messages[i];
            System.out.println("主题: " + getSubject(msg));
            String from=getFrom(msg);
            int index1=from.lastIndexOf('>');
            String from2=from.substring(0,index1);
            int index2=from2.lastIndexOf('<');
            from=from.substring(index2+1,index1);//发件人
            String subject=getSubject(msg);//主题
            if(from.equals(user.getEmail())&&"改绑邮箱".equals(subject))
            {
                flag=true;
                deleteMessage(msg);
            }
            else if(!"改绑邮箱".equals(subject))
                deleteMessage(msg);
        }
        return flag;
    }
    private static void deleteMessage(Message messages) throws MessagingException, IOException {
        if (messages == null )
            throw new MessagingException("未找到要解析的邮件!");
        messages.setFlag(Flags.Flag.DELETED, true);
    }
    private static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
    }
    private static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }
}
