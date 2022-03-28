package es.grouppayments.backend.users.userexistsbyemail;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class UserExistsByEmailTest extends UserExistsByEmailTestMother{
    @Test
    public void shouldFind(){
        final String email = "email1@gmail.com";
        addUser(UUID.randomUUID(), email);

        assertTrue(execute(new UserExistsByEmailQuery(email)).isExists());
    }

    @Test
    public void shouldntFind(){
        final String email = "emai2l@gmail.com";
        assertFalse(execute(new UserExistsByEmailQuery(email)).isExists());
    }
}
