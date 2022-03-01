package es.grouppayments.backend.groups.delete;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import org.junit.Test;

import java.util.UUID;

public class DeleteGroupTest extends DeleteGroupTestMother{
    @Test
    public void shouldDeleteGroup(){
        UUID groupId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        addGroup(groupId, userId);
        executeDeleteGroup(groupId, userId);

        assertGroupDeleted(groupId);
        assertEventRaised(GroupDeleted.class);
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntDeleteGroupWhenNotTheAdmin(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());

        executeDeleteGroup(groupId, UUID.randomUUID());
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntDeleteGroupWhenNotExists(){
        executeDeleteGroup(UUID.randomUUID(), UUID.randomUUID());
    }
}
