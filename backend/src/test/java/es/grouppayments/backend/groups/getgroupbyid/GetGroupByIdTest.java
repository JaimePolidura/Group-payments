package es.grouppayments.backend.groups.getgroupbyid;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class GetGroupByIdTest extends GetGroupByIdTestMother{
    @Test
    public void shouldFind(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());

        Group result = executeQuery(groupId).getGroup();

        assertEquals(groupId, result.getGroupId());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntFind(){
        executeQuery(UUID.randomUUID());
    }
}
