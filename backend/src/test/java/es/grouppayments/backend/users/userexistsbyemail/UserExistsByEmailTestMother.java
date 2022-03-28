package es.grouppayments.backend.users.userexistsbyemail;

import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users._shared.domain.UsersService;

public class UserExistsByEmailTestMother extends UsersTestMother {
    private final UserExistsByEmailQueryHandler queryHandler;

    public UserExistsByEmailTestMother(){
        this.queryHandler = new UserExistsByEmailQueryHandler(
                new UsersService(usersRepository())
        );
    }

    public UserExistsByEmailQueryResponse execute(UserExistsByEmailQuery query){
        return this.queryHandler.handle(query);
    }
}
