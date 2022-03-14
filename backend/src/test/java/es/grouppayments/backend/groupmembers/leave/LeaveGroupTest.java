package es.grouppayments.backend.groupmembers.leave;

import es.grouppayments.backend.groupmembers._shared.domain.events.GroupMemberLeft;
import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

public class LeaveGroupTest extends LeaveGroupMother{
    @Test
    public void shouldDeleteMemberWhenUser(){
        UUID userAdmin = UUID.randomUUID();
        UUID userMember = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userAdmin, 100, userMember);

        execute(userMember, groupId);

        assertMemberDeleted(userMember);
        assertEventRaised(GroupMemberLeft.class);
    }

    @Test
    public void shouldDeleteMemberAndGroupWhenAdmin(){
        UUID userAdmin = UUID.randomUUID();
        UUID userMember = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userAdmin, 100, userMember);

        execute(userAdmin, groupId);

        assertGroupDeleted(groupId);
        assertEventRaised(GroupDeleted.class);
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntDeleteGroupNotExists(){
        execute(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntDeleteNotMemberOfGroup(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());

        execute(UUID.randomUUID(), groupId);
    }

    @Test(expected = IllegalState.class)
    public void shouldntDeleteInvalidState(){
        UUID userAdmin = UUID.randomUUID();
        UUID userMember = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userAdmin, 100, userMember);
        changeStateTo(groupId, GroupState.PAYING);

        execute(userMember, groupId);
    }
}
