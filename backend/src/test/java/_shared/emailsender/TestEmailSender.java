package _shared.emailsender;

import es.grouppayments.backend._shared.domain.EmailSender;

public interface TestEmailSender extends EmailSender {
    String getContent(String to);
    String getSubject(String to);
    boolean hasEmail(String to);
}
