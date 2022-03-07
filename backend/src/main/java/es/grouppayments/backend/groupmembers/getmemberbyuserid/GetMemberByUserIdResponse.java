package es.grouppayments.backend.groupmembers.getmemberbyuserid;

import es.grouppayments.backend.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class GetMemberByUserIdResponse implements QueryResponse {
    @Getter private final UUID userId;
    @Getter private final String username;
    @Getter private final String email;
    @Getter private final String photoUrl;

    public static GetMemberByUserIdResponse fromUserAggregate(User userAggregate){
        return new GetMemberByUserIdResponse(
                userAggregate.getUserId(),
                userAggregate.getUsername(),
                userAggregate.getEmail(),
                userAggregate.getPhotoUrl()
        );
    }
}
