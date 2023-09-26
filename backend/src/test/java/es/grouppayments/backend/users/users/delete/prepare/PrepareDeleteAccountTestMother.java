package es.grouppayments.backend.users.users.delete.prepare;

import _shared.emailsender.FakeEmailSender;
import _shared.emailsender.TestEmailSender;
import _shared.emailsender.UsingTestEmailSender;
import _shared.token.FakeTokenService;
import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend._shared.application.ConfirmTokenService;

public class PrepareDeleteAccountTestMother extends UsersTestMother implements UsingTestEmailSender {
    private final TestEmailSender testEmailSender;
    private final PrepareDeleteAccountCommandHandler commandHandler;

    public PrepareDeleteAccountTestMother(){
        this.testEmailSender = new FakeEmailSender();
        this.commandHandler = new PrepareDeleteAccountCommandHandler(
                this.testEmailSender,
                new ConfirmTokenService(new FakeTokenService()),
                new UsersService(super.usersRepository(), super.testEventBus)
        );
    }

    @Override
    public TestEmailSender testEmailSender() {
        return this.testEmailSender;
    }

    public void execute(PrepareDeleteAccountCommand command){
        this.commandHandler.handle(command);
    }
}
