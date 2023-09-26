package es.grouppayments.backend.users.users.getuserdatabyuserid;

import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class GetUserDataByUserIdQueryHandler implements QueryHandler<GetUserDataByUserIdQuery, GetUserDataByUserIdQueryResponse> {
    private final UsersService usersService;

    @Override
    public GetUserDataByUserIdQueryResponse handle(GetUserDataByUserIdQuery query) {
        User user = this.usersService.getByUserId(query.getUserId());

        return new GetUserDataByUserIdQueryResponse(
                user.getUsername(),
                user.getEmail(),
                user.getUserImageId()
        );
    }
}
