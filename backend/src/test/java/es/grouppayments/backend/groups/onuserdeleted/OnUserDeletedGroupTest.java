package es.grouppayments.backend.groups.onuserdeleted;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import org.junit.Test;

import java.util.UUID;

public final class OnUserDeletedGroupTest extends OnUserDeletedGroupsTestMother{
    @Test
    public void shouldDelete(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId);

        super.on(new UserDeleted(userId));

        assertEventRaised(GroupDeleted.class);
        assertGroupDeleted(groupId);
    }

    @Test
    public void shouldntDeleteNoGroup(){
        //shouldnt throw any exception
        super.on(new UserDeleted(UUID.randomUUID()));
    }
}
