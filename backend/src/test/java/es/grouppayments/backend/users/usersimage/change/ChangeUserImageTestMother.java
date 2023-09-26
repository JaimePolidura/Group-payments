package es.grouppayments.backend.users.usersimage.change;

import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.usersimage._shared.application.UsersImagesService;

public abstract class ChangeUserImageTestMother extends UsersTestMother {
    private final ChangeUserImageCommandHandler handler;

    public ChangeUserImageTestMother() {
        this.handler = new ChangeUserImageCommandHandler(
                new UsersImagesService(super.userImagesRepository()),
                new UsersService(super.usersRepository(), super.testEventBus),
                inital -> inital
        );
    }

    public void handle(ChangeUserImageCommand command){
        this.handler.handle(command);
    }
}
