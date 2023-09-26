package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class GetCurrentGroupByUserIdTest extends GetCurrentGroupByUserIdMother {
    @Test
    public void shouldFind(){
        UUID userIdAdmin = UUID.randomUUID();
        UUID userIdMember1 = UUID.randomUUID();
        UUID userIdMember2 = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userIdAdmin);
        addUser(userIdAdmin, userIdMember1, userIdMember2);
        addMember(groupId, userIdMember1, userIdMember2);

        GetCurrentGroupByUserQueryResponse response = executeQuery(userIdAdmin);

        assertEquals(groupId, response.getGroup().getGroupId());
        assertCollectionSize(response.getMembers(), 3);
    }

    @Test
    public void shouldFindOnlyAdmin(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId);
        addUser(userId);

        GetCurrentGroupByUserQueryResponse response = executeQuery(userId);

        assertEquals(groupId, response.getGroup().getGroupId());
        assertCollectionSize(response.getMembers(), 1);
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntFind(){
        GetCurrentGroupByUserQueryResponse response = executeQuery(UUID.randomUUID());

        assertNull(response.getGroup());
    }
}
