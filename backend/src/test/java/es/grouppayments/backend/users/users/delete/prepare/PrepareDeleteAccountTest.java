package es.grouppayments.backend.users.users.delete.prepare;

import org.junit.Test;

import java.util.UUID;

public final class PrepareDeleteAccountTest extends PrepareDeleteAccountTestMother{
    @Test
    public void shouldSendEmail(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        execute(new PrepareDeleteAccountCommand(userId));

        assertHasEmail(DEFAULT_EMAIL);
        assertContentContains(DEFAULT_EMAIL, userId.toString());
        assertSubject(DEFAULT_EMAIL, PrepareDeleteAccountCommandHandler.DELETE_ACCOUNT_EMAIL_SUBJECT);
    }
}
