package es.grouppayments.backend.groupmembers.ongroupdeletedtest;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import org.junit.Test;

import java.util.UUID;

public class OnGroupDeletedTest extends OnGroupDeletedMother{

    @Test
    public void shouldDelete(){
        UUID groupId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID user3 = UUID.randomUUID();
        addGroup(groupId, user1);
        addMember(groupId, user2, user3);

        execute(new GroupDeleted(groupId));

        assertMemberDeleted(user1);
    }
}
