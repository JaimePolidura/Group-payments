package es.grouppayments.backend.users.users.getuserdatabyuserid;

import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users.users._shared.application.UsersService;

public class UserDataByUserIdTestMother extends UsersTestMother {
    private final GetUserDataByUserIdQueryHandler handler;

    public UserDataByUserIdTestMother(){
        this.handler = new GetUserDataByUserIdQueryHandler(
                new UsersService(super.usersRepository(), super.testEventBus)
        );
    }

    public GetUserDataByUserIdQueryResponse execute(GetUserDataByUserIdQuery query){
        return this.handler.handle(query);
    }
}
