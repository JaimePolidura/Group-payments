package es.grouppayments.backend.groupmembers.findmembersbygroupid;

import es.grouppayments.backend.users._shared.domain.User;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class FindMembersByGroupIdTest extends FindMembersByGroupIdMother {
    @Test
    public void shouldFind(){
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();

        addUser(user1, user2);
        addGroup(groupId, user1);
        addMember(groupId, user2);

        List<User> usersResponse = executeQuery(groupId)
                .getUsers();

        assertEquals(2, usersResponse.size());
        assertTrue(usersResponse.stream().anyMatch(user -> user.getUserId().equals(user1)));
        assertTrue(usersResponse.stream().anyMatch(user -> user.getUserId().equals(user2)));
    }

    @Test
    public void shouldntFind(){
        assertEmptyCollection(executeQuery(UUID.randomUUID()).getUsers());
    }
}
