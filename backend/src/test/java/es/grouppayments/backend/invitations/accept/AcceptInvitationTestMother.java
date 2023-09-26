package es.grouppayments.backend.invitations.accept;

import es.grouppayments.backend.groupmembers.join.JoinGroupCommand;
import es.grouppayments.backend.groupmembers.join.JoinGroupCommandHandler;
import es.grouppayments.backend.invitations.InvitationTestMother;
import es.grouppayments.backend.invitations._shared.application.InvitationService;

import java.util.function.Predicate;

import static org.junit.Assert.*;


public class AcceptInvitationTestMother extends InvitationTestMother {
    private final AcceptInvitationCommandHandler commandHandler;
    private final FakeJoinGroupCommandHandler joinGroupCommandHandler;

    public AcceptInvitationTestMother(){
        this.joinGroupCommandHandler = new FakeJoinGroupCommandHandler();
        this.commandHandler = new AcceptInvitationCommandHandler(
                new InvitationService(super.invitationsRepository(), super.testEventBus),
                this.joinGroupCommandHandler,
                super.testEventBus
        );
    }

    public void execute(AcceptInvitationCommand command){
        this.commandHandler.handle(command);
    }

    protected void assertJoinGroupCommandDisptched(){
        assertNotNull(this.joinGroupCommandHandler.dispatchedCommand);
        assertEquals(this.joinGroupCommandHandler.dispatchedCommand.getClass(), JoinGroupCommand.class);
    }

    protected void assertContentOfJoinGroupCommand(Predicate<JoinGroupCommand> condition){
        assertTrue(condition.test(this.joinGroupCommandHandler.dispatchedCommand));
    }

    private static final class FakeJoinGroupCommandHandler extends JoinGroupCommandHandler {
        private JoinGroupCommand dispatchedCommand;

        public FakeJoinGroupCommandHandler() {
            super(null, null, null);
        }

        @Override
        public void handle(JoinGroupCommand command) {
            this.dispatchedCommand = command;
        }
    }
}
