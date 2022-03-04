package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class GetMembersByGroupIdTest extends GetMembersByGroupIdMother {
    @Test
    public void shouldFind(){
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();

        addGroup(groupId, user1, 10, user2);
        addUser(user1, user2);

        List<GetMembersByGroupIdQueryResponse.GroupMemberUser> usersResponse = executeQuery(groupId)
                .getMembers();

        assertEquals(2, usersResponse.size());
        assertTrue(usersResponse.stream().anyMatch(user -> user.getUserId().equals(user1)));
        assertTrue(usersResponse.stream().anyMatch(user -> user.getUserId().equals(user2)));
    }

    @Test
    public void shouldntFind(){
        assertEmptyCollection(executeQuery(UUID.randomUUID()).getMembers());
    }
}
