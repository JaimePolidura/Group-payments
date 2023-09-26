package es.grouppayments.backend.groupmembers.ongruopcreated;

import es.grouppayments.backend.groups.create.GroupCreated;
import org.junit.Test;

import java.util.UUID;

public class OnGroupCreatedTest extends OnGroupCreatedMother {
    @Test
    public void shouldAddMemberAdmin(){
        UUID groupId = UUID.randomUUID();
        UUID adminUserId =  UUID.randomUUID();

        triggerEventListener(new GroupCreated(groupId, adminUserId));

        assertMemberInGroup(groupId, adminUserId);
    }
}
