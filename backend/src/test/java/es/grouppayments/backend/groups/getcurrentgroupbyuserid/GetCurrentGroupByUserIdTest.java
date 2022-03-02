package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class GetCurrentGroupByUserIdTest extends GetCurrentGroupByUserIdMother {
    @Test
    public void shouldFind(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId);

        GetCurrentGroupByUserQueryResponse response = executeQuery(userId);

        assertEquals(groupId, response.getGroup().getGroupId());
    }

    @Test
    public void shouldntFind(){
        GetCurrentGroupByUserQueryResponse response = executeQuery(UUID.randomUUID());

        assertNull(response.getGroup());
    }
}
