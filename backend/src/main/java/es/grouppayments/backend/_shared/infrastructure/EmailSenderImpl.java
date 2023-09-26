package es.grouppayments.backend._shared.infrastructure;

import es.grouppayments.backend._shared.domain.EmailSender;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSenderImpl implements EmailSender {
    private final String account;
    private final String password;

    public EmailSenderImpl(@Value("${grouppayments.email.account}") String account,
                           @Value("${grouppayments.email.password}") String password) {
        this.account = account;
        this.password = password;
    }

    @SneakyThrows
    public void send(String to, String subejct, String content) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new EmailAuthenticator());

        Message message = buildMessage(session, account, to, subejct, content);

        Transport.send(message);
    }

    @SneakyThrows
    private Message buildMessage(Session session, String sender, String to, String subject, String content) {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(content);

        return message;
    }

    private final class EmailAuthenticator extends Authenticator{
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(account, password);
        }
    }
}
