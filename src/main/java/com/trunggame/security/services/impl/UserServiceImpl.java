package com.trunggame.security.services.impl;

import com.trunggame.dto.SignupRequestDTO;
import com.trunggame.dto.ValidateRequestDTO;
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
import java.util.Objects;
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
                "<p> Welcome to join with us on Trung Games website <br/>\n" +
                "Please keep secret your password: <strong>" + user.getPassword() + "</strong><br/>\n" +
                "Please change your password immediately after a first login!<br/> \n" +
                "Have a good experience on our service!</p>";

        this.sendAsHtml(user.getEmail(),
                "[TRUNGGAMES] Congratulation! You have registered account successfully!",
                html);
        return true;
    }

    @Override
    public Boolean sendEmailRegister() throws MessagingException {

        String html = "<h2>Hi Bạn </h2><br/>" +
                "<p> You have created new account on Trung Games website\n" +
                "Please keep secret your password: \n" +
                "Change your password immediately after a first login! \n" +
                "Have a good experience on our service!</p>";

        this.sendAsHtml("leanhvu86@outlook.com",
                "Congratulation! You have registered account successfully!",
                html);
        return true;
    }

    @Override
    public String validatePhoneAndEmail(ValidateRequestDTO signupRequestDTO) {
        if (!Objects.equals(signupRequestDTO.getEmail(), "")) {
            var user = userRepository.existsByUsername(signupRequestDTO.getEmail());
            if (user) {
                return "Email is exist!";
            }
        }
        if (!Objects.equals(signupRequestDTO.getPhoneNumber(), "")) {
            var user = userRepository.existsByPhoneNumber(signupRequestDTO.getPhoneNumber());
            if (user) {
                return "Phone is exist!";
            }
        }
        return "";
    }

    @Override
    public Boolean forgetPassword(ValidateRequestDTO signupRequestDTO) throws MessagingException {
        var user = userRepository.findByUsername(signupRequestDTO.getUsername());
        if (user.isPresent()) {
            String password = RandGeneratedStr(10);
            user.get().setPassword(password);
            userRepository.save(user.get());
            sendEmailRegister(user.get());
            return true;
        }
        return false;
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
        props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    public String RandGeneratedStr(int l) {

        // a list of characters to choose from in form of a string

        String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789!@#$%^&*";

        // creating a StringBuffer size of AlphaNumericStr

        StringBuilder s = new StringBuilder(l);

        int i;

        for (i = 0; i < l; i++) {

            //generating a random number using math.random()

            int ch = (int) (randomString.length() * Math.random());

            //adding Random character one by one at the end of s

            s.append(randomString.charAt(ch));

        }

        return s.toString();

    }

}
