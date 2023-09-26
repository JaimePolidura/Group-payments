package es.grouppayments.backend.users.users.getuserdatabyuserid;

import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public final class UserDataByUserIdTest extends UserDataByUserIdTestMother {
    @Test
    public void shouldGet(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        GetUserDataByUserIdQueryResponse response = execute(new GetUserDataByUserIdQuery(userId.toString()));

        assertEquals(DEFAULT_USERNAME, response.getUsername());
        assertEquals(DEFAULT_EMAIL, response.getEmail());
        assertEquals(DEFAULT_IMAGE_ID, response.getUserImageId());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntGet(){
        String usernameFound = execute(new GetUserDataByUserIdQuery(UUID.randomUUID().toString()))
                .getUsername();
    }
}
