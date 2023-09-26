package es.grouppayments.backend.invitations.create;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.invitations.InvitationTestMother;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.users.users._shared.application.UsersService;

public class CreateInvitationTestMother extends InvitationTestMother {
    private final CreateInvitationCommandHandler commandHandler;

    public CreateInvitationTestMother(){
        this.commandHandler = new CreateInvitationCommandHandler(
                new UsersService(super.usersRepository(), super.testEventBus),
                new GroupService(super.groupRepository(), super.testEventBus),
                new GroupMemberService(super.groupMemberRepository(), super.testEventBus),
                new InvitationService(super.invitationsRepository(), super.testEventBus)
        );
    }

    public void execute(CreateInvitationCommand command){
        this.commandHandler.handle(command);
    }
}
