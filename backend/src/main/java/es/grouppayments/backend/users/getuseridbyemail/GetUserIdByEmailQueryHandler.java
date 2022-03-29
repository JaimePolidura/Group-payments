package es.grouppayments.backend.users.getuseridbyemail;

import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class GetUserIdByEmailQueryHandler implements QueryHandler<GetUserIdByEmailQuery, GetUserIdByEmailQueryResponse> {
    private final UsersService usersService;

    @Override
    public GetUserIdByEmailQueryResponse handle(GetUserIdByEmailQuery query) {
        UUID userId = this.usersService.findByEmail(query.getEmail())
                .orElseThrow(() -> new ResourceNotFound("User not found for that email"))
                .getUserId();

        return new GetUserIdByEmailQueryResponse(userId);
    }
}
