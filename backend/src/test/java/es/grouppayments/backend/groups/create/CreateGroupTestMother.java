package es.grouppayments.backend.groups.create;

import _shared.invitations.UsingInvitations;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups.GroupTestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.invitations._shared.domain.InvitationRepository;
import es.grouppayments.backend.invitations._shared.infrastructure.InvitationRepositoryInMemory;
import es.grouppayments.backend.users.users._shared.application.UsersService;

import java.util.List;
import java.util.UUID;

public class CreateGroupTestMother extends GroupTestMother implements UsingInvitations {
    protected final CreateGroupCommandHandler createGroupCommandHandler;
    protected final InvitationRepository invitationRepository;

    public CreateGroupTestMother(){
        this.invitationRepository = new InvitationRepositoryInMemory();
        this.createGroupCommandHandler = new CreateGroupCommandHandler(
                new GroupService(groupRepository(), this.testEventBus),
                new GroupMemberService(groupMemberRepository(), this.testEventBus),
                new UsersService(super.usersRepository(), super.testEventBus),
                new InvitationService(this.invitationRepository, super.testEventBus)
        );
    }

    protected void executeCreateGroupCommandHandler(UUID idOfGroupToCreate){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        this.createGroupCommandHandler.handle(
                new CreateGroupCommand(idOfGroupToCreate, userId, 100, "group", null)
        );
    }

    protected void executeCreateGroupCommandHandler(UUID idOfGroupToCreate, UUID userId){
        this.createGroupCommandHandler.handle(
                new CreateGroupCommand(idOfGroupToCreate, userId, 100, "group", null)
        );
    }

    protected void executeCreateGroupCommandHandler(UUID idOfGroupToCreate, UUID userId, List<String> emailsUsersToInvite){
        this.createGroupCommandHandler.handle(
                new CreateGroupCommand(idOfGroupToCreate, userId, 100, "group", emailsUsersToInvite)
        );
    }

    @Override
    public InvitationRepository invitationsRepository() {
        return this.invitationRepository;
    }
}
