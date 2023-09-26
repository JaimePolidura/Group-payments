package _shared.emailsender;

import static org.junit.Assert.*;

public interface UsingTestEmailSender {
    TestEmailSender testEmailSender();

    default void assertHasEmail(String to){
        assertTrue(this.testEmailSender().hasEmail(to));
    }

    default void assertContent(String to, String content){
        assertTrue(this.testEmailSender().getContent(to).equalsIgnoreCase(content));
    }

    default void assertContentContains(String to, String content){
        assertTrue(this.testEmailSender().getContent(to).contains(content));
    }

    default void assertSubject(String to, String subject){
        assertTrue(this.testEmailSender().getSubject(to).equalsIgnoreCase(subject));
    }
}
