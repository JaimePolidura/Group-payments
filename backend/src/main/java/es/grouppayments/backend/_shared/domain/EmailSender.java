package es.grouppayments.backend._shared.domain;

public interface EmailSender {
    void send(String to, String subject, String content);
}
