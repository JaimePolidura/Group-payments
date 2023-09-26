package es.grouppayments.backend.users.users.getuseridbyemail;

import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users.users._shared.application.UsersService;

public class UserExistsByEmailTestMother extends UsersTestMother {
    private final GetUserIdByEmailQueryHandler queryHandler;

    public UserExistsByEmailTestMother(){
        this.queryHandler = new GetUserIdByEmailQueryHandler(
                new UsersService(usersRepository(), super.testEventBus)
        );
    }

    public GetUserIdByEmailQueryResponse execute(GetUserIdByEmailQuery query){
        return this.queryHandler.handle(query);
    }
}
