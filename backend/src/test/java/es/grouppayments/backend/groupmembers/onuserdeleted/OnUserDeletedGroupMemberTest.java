package es.grouppayments.backend.groupmembers.onuserdeleted;

import es.grouppayments.backend.groupmembers.leave.GroupMemberLeft;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import org.junit.Test;

import java.util.UUID;

public final class OnUserDeletedGroupMemberTest extends OnUserDeletedGroupMemberTestMother{
    @Test
    public void shouldDelete(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID(), 10, UUID.randomUUID(), userId);

        on(new UserDeleted(userId));

        assertEventRaised(GroupMemberLeft.class);
        assertMemberDeleted(userId);
    }

    @Test
    public void shouldntDelete(){
        on(new UserDeleted(UUID.randomUUID()));
    }
}
