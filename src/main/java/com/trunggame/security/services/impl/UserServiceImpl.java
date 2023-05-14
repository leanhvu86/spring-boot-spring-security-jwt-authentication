package com.trunggame.security.services.impl;

import com.trunggame.models.User;
import com.trunggame.repository.UserRepository;
import com.trunggame.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService {
    @Value("${admin.email}")
    private String senderEmail;//change with your sender email

    @Value("${admin.password}")
    private String senderPassword;//change with your sender email

    @Autowired
    UserRepository userRepository;

    @Override
    public void deleteUserByIds(List ids) {
        userRepository.deleteUserByIds(ids);
    }

    @Override
    public void activeUserByIds(List ids) {
        userRepository.activeUserByIds(ids);
    }

    @Override
    public Boolean sendEmailRegister(User user) throws MessagingException {

        String html = "<h2>Hi " + user.getUsername() + "</h2><br/>" +
                "<p> You have created new account on Trung Games website\n" +
                "Please keep secret your password: " + user.getPassword() + "\n" +
                "Change your password immediately after a first login! \n" +
                "Have a good experience on our service!</p>";

        this.sendAsHtml(user.getEmail(),
                "Congratulation! You have registered account successfully!",
                html);
        return true;
    }

    public void sendAsHtml(String to, String title, String html) throws MessagingException {
        System.out.println("Sending email to " + to);

        Session session = createSession();

        //create message using session
        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, to, title, html);

        //sending message
        Transport.send(message);
        System.out.println("Done");
    }

    private void prepareEmailMessage(MimeMessage message, String to, String title, String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
        props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
        props.put("mail.smtp.host", "smtp.1and1.com"); //Outgoing server (SMTP) - change it to your SMTP server
        props.put("mail.smtp.port", "587");//Outgoing port

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }
}
