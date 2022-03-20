package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
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

    @Test(expected = ResourceNotFound.class)
    public void shouldntFind(){
        GetCurrentGroupByUserQueryResponse response = executeQuery(UUID.randomUUID());

        assertNull(response.getGroup());
    }
}
