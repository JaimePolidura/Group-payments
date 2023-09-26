package es.grouppayments.backend.invitations.getbyuserid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class GetInvitationsByUserIdQuery implements Query {
    @Getter private final UUID userId;
}
