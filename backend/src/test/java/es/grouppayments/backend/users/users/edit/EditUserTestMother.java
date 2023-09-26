package es.grouppayments.backend.users.users.edit;

import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.usersimage._shared.application.UsersImagesService;

import java.util.UUID;

public class EditUserTestMother extends UsersTestMother {
    private final EditUserCommandHandler handler;

    public EditUserTestMother(){
        this.handler = new EditUserCommandHandler(
                new UsersService(super.usersRepository(), super.testEventBus),
                new UsersImagesService(super.userImagesRepository())
        );
    }

    public void execute(UUID userId, String username, Currency currency, String countryCode){
        this.handler.handle(new EditUserCommand(userId, username, currency, countryCode));
    }

    public void execute(EditUserCommand command){
        this.handler.handle(command);
    }
}
