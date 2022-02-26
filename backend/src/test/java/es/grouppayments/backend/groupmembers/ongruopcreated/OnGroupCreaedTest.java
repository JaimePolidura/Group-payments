package es.grouppayments.backend.groupmembers.ongruopcreated;

import es.grouppayments.backend.groups._shared.domain.events.GroupCreated;
import org.junit.Test;

import java.util.UUID;

public class OnGroupCreaedTest extends OnGroupCreaedMother{
    @Test
    public void shouldAddMemberAdmin(){
        UUID groupId = UUID.randomUUID();
        UUID adminUserId =  UUID.randomUUID();

        triggerEventListener(new GroupCreated(groupId, adminUserId));

        assertMemberInGroup(groupId, adminUserId);
    }
}
