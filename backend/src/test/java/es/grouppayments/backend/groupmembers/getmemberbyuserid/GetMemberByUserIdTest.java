package es.grouppayments.backend.groupmembers.getmemberbyuserid;

import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class GetMemberByUserIdTest extends GetMemberByUserIdTestMother{
    @Test
    public void shouldGet(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID userIdToGet = UUID.randomUUID();
        addGroup(groupId, userId, 1, userIdToGet);
        addUser(userIdToGet);

        GetMemberByUserIdQueryResponse response = execute(groupId, userIdToGet, userId);

        assertNotNull(response);
        assertEquals(userIdToGet, response.getUserId());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntGetGroupNotExists(){
        execute(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntGetUserNotInGroup(){
        UUID userIdToGet = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID(), 10, userIdToGet);

        execute(groupId, userIdToGet, UUID.randomUUID());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntGetUserToGetNotInGroup(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId, 10, UUID.randomUUID(), UUID.randomUUID());

        execute(groupId, UUID.randomUUID(), userId);
    }
}
