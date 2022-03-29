package es.grouppayments.backend.users.getuseridbyemail;

import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users._shared.domain.UsersService;

public class UserExistsByEmailTestMother extends UsersTestMother {
    private final GetUserIdByEmailQueryHandler queryHandler;

    public UserExistsByEmailTestMother(){
        this.queryHandler = new GetUserIdByEmailQueryHandler(
                new UsersService(usersRepository())
        );
    }

    public GetUserIdByEmailQueryResponse execute(GetUserIdByEmailQuery query){
        return this.queryHandler.handle(query);
    }
}
