package es.grouppayments.backend.gruops.delete;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

public class DeleteGroupTest extends DeleteGroupTestMother{
    @Test
    public void shouldDeleteGroup(){
        UUID gruopId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        addGroup(gruopId, userId);
        executeDeleteGroup(gruopId, userId);

        assertGroupDeleted(gruopId);
        assertEventRaised(GroupDeleted.class);
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntDeleteGroupWhenNotTheAdmin(){
        UUID gruopId = UUID.randomUUID();

        addGroup(gruopId, UUID.randomUUID());

        executeDeleteGroup(gruopId, UUID.randomUUID());
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntDeleteGroupWhenNotExists(){
        executeDeleteGroup(UUID.randomUUID(), UUID.randomUUID());
    }
}
