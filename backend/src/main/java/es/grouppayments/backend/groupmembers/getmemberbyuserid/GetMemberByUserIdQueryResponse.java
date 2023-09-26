package es.grouppayments.backend.groupmembers.getmemberbyuserid;

import es.grouppayments.backend.users.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class GetMemberByUserIdQueryResponse implements QueryResponse {
    @Getter private final UUID userId;
    @Getter private final String username;
    @Getter private final String email;
    @Getter private final int userImageId;

    public static GetMemberByUserIdQueryResponse fromUserAggregate(User userAggregate){
        return new GetMemberByUserIdQueryResponse(
                userAggregate.getUserId(),
                userAggregate.getUsername(),
                userAggregate.getEmail(),
                userAggregate.getUserImageId()
        );
    }
}
