package es.grouppayments.backend.users.getuseridbyemail;

import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public final class UserExistsByEmailTest extends UserExistsByEmailTestMother{
    @Test
    public void shouldFind(){
        final String email = "email1@gmail.com";
        UUID userId = UUID.randomUUID();
        addUser(userId, email);

        assertEquals(userId, execute(new GetUserIdByEmailQuery(email)).getUserId());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntFind(){
        final String email = "emai2l@gmail.com";
        execute(new GetUserIdByEmailQuery(email));
    }
}
