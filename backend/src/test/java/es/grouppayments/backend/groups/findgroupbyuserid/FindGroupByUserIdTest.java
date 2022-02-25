package es.grouppayments.backend.groups.findgroupbyuserid;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class FindGroupByUserIdTest extends FindGroupByUserIdMother{
    @Test
    public void shouldFind(){
        UUID userId = UUID.randomUUID();
        UUID gruopId = UUID.randomUUID();
        addGroup(gruopId, userId);
        addGroup(UUID.randomUUID(), UUID.randomUUID());

        FindGroupByUserQueryResponse response = executeQuery(userId);

        assertEquals(gruopId, response.getGroup().getGroupId());
    }

    @Test
    public void shouldntFind(){
        FindGroupByUserQueryResponse response = executeQuery(UUID.randomUUID());

        assertNull(response.getGroup());
    }
}
