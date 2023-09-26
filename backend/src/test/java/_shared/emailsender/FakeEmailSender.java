package _shared.emailsender;


import java.util.HashMap;
import java.util.Map;

public final class FakeEmailSender implements TestEmailSender {
    private final Map<String, Email> lastEmails;

    public FakeEmailSender(){
        this.lastEmails = new HashMap<>();
    }

    @Override
    public void send(String to, String subject, String content) {
        this.lastEmails.put(to, new Email(to, subject, content));
    }

    @Override
    public String getContent(String to) {
        return this.lastEmails.get(to).content;
    }

    @Override
    public String getSubject(String to) {
        return this.lastEmails.get(to).subject;
    }

    @Override
    public boolean hasEmail(String to) {
        return this.lastEmails.get(to) != null;
    }

    private record Email(String to, String subject, String content){}
}
