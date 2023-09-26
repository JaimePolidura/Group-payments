package es.grouppayments.backend.users.users.delete.confirm;

import _shared.token.FakeTokenService;
import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend._shared.application.ConfirmTokenService;

public class ConfirmUserDeleteTestMother extends UsersTestMother {
    private final ConfirmDeleteAccountCommandHandler commandHandler;
    protected final FakeTokenService tokenService;

    public ConfirmUserDeleteTestMother(){
        this.tokenService = new FakeTokenService();
        this.commandHandler = new ConfirmDeleteAccountCommandHandler(
                new ConfirmTokenService(this.tokenService),
                new UsersService(super.usersRepository(), super.testEventBus)
        );
    }

    public void execute(ConfirmDeleteAccountCommand command){
        this.commandHandler.handle(command);
    }
}
