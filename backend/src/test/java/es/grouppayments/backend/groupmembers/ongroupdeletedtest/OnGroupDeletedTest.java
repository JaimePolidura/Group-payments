package es.grouppayments.backend.groupmembers.ongroupdeletedtest;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import org.junit.Test;

import java.util.UUID;

public class OnGroupDeletedTest extends OnGroupDeletedMother{
    @Test
    public void shouldDelete(){
        UUID groupId = UUID.randomUUID();
        UUID userAdmin = UUID.randomUUID();
        addGroup(groupId, userAdmin, 10, UUID.randomUUID(), UUID.randomUUID());

        execute(new GroupDeleted(groupId));

        assertMemberDeleted(userAdmin);
    }
}
