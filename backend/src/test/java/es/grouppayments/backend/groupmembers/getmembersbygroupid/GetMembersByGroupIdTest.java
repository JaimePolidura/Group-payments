package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.grouppayments.backend.groupmembers.getmemberbyuserid.GetMemberByUserIdResponse;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class GetMembersByGroupIdTest extends GetMembersByGroupIdMother {
    @Test
    public void shouldGet(){
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();

        addGroup(groupId, user1, 10, user2);
        addUser(user1, user2);

        List<GetMemberByUserIdResponse> usersResponse = executeQuery(groupId, user1)
                .getMembers();

        assertEquals(2, usersResponse.size());
        assertTrue(usersResponse.stream().anyMatch(user -> user.getUserId().equals(user1)));
        assertTrue(usersResponse.stream().anyMatch(user -> user.getUserId().equals(user2)));
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntGetGroupNotExists(){
        executeQuery(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntGetNotBelongsToGroup(){
        UUID userToGet = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID(), 100);

        executeQuery(groupId, UUID.randomUUID());
    }
}
