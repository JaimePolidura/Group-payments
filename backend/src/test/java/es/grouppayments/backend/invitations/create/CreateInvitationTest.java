package es.grouppayments.backend.invitations.create;

import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.jaime.javaddd.domain.exceptions.AlreadyExists;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

public final class CreateInvitationTest extends CreateInvitationTestMother{
    @Test
    public void shouldInvite(){
        UUID invitationId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID userFrom = UUID.randomUUID();
        UUID userTo = UUID.randomUUID();
        addUser(userFrom, userTo);
        addGroup(groupId, userFrom);

        execute(new CreateInvitationCommand(groupId, userTo, userFrom, invitationId));

        assertInvitationCreated(invitationId);
        assertEventRaised(InvitationCreated.class);
        assertContentOfInvitation(invitationId, invitiation -> invitiation.getFromUserId().equals(userFrom));
        assertContentOfInvitation(invitationId, invitiation -> invitiation.getToUserId().equals(userTo));
        assertContentOfInvitation(invitationId, invitiation -> invitiation.getGroupId().equals(groupId));
    }

    @Test(expected = ResourceNotFound.class)
    public void groupNotExists(){
        execute(new CreateInvitationCommand(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test(expected = IllegalState.class)
    public void invalidGroupState(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, GroupState.PAYING);

        execute(new CreateInvitationCommand(groupId, UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test(expected = ResourceNotFound.class)
    public void userToNotExist(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());

        execute(new CreateInvitationCommand(groupId, UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test(expected = ResourceNotFound.class)
    public void userFromIsNotInAnyGroup(){
        UUID groupId = UUID.randomUUID();
        UUID userTo = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());
        addUser(userTo);

        execute(new CreateInvitationCommand(groupId, userTo, UUID.randomUUID()));
    }


    @Test(expected = NotTheOwner.class)
    public void userFromIsInOtherGroup(){
        //User to belongs to group
        UUID groupId = UUID.randomUUID();
        UUID userTo = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());
        addUser(userTo);
        //Belongs to other group
        UUID userFrom = UUID.randomUUID();
        addGroup(UUID.randomUUID(), userFrom);

        execute(new CreateInvitationCommand(groupId, userTo, userFrom));
    }

    @Test(expected = AlreadyExists.class)
    public void userToBelongsToOtherGroup(){
        UUID groupId = UUID.randomUUID();
        UUID userFrom = UUID.randomUUID();
        UUID userTo = UUID.randomUUID();
        addUser(userTo, userFrom);

        addGroup(groupId, userFrom);
        addGroup(UUID.randomUUID(), userTo);

        execute(new CreateInvitationCommand(groupId, userTo, userFrom));
    }

    @Test(expected = AlreadyExists.class)
    public void userAlreadyInvitedToGroup(){
        UUID groupId = UUID.randomUUID();
        UUID userFrom = UUID.randomUUID();
        UUID userTo = UUID.randomUUID();
        addUser(userFrom, userTo);
        addGroup(UUID.randomUUID(), userTo);
        addGroup(groupId, userFrom);

        execute(new CreateInvitationCommand(groupId, userTo, userFrom));
    }
}
