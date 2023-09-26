package es.grouppayments.backend.invitations.getbyuserid;

import es.grouppayments.backend.invitations._shared.domain.Invitation;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public final class GetInvitationsByUserIdQueryResponse implements QueryResponse {
    @Getter private final List<Invitation> invitiations;
}
