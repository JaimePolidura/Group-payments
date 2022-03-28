package es.grouppayments.backend.users.userexistsbyemail;

import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class UserExistsByEmailQueryHandler implements QueryHandler<UserExistsByEmailQuery, UserExistsByEmailQueryResponse> {
    private final UsersService usersService;

    @Override
    public UserExistsByEmailQueryResponse handle(UserExistsByEmailQuery query) {
        var userFound = this.usersService.findByEmail(query.getEmail())
                .isPresent();

        return new UserExistsByEmailQueryResponse(userFound);
    }
}
