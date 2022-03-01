package es.grouppayments.backend.groups.findgroupbyuserid;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class FindGroupByUserIdTest extends FindGroupByUserIdMother{
    @Test
    public void shouldFind(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId);

        FindGroupByUserQueryResponse response = executeQuery(userId);

        assertEquals(groupId, response.getGroup().getGroupId());
    }

    @Test
    public void shouldntFind(){
        FindGroupByUserQueryResponse response = executeQuery(UUID.randomUUID());

        assertNull(response.getGroup());
    }
}
