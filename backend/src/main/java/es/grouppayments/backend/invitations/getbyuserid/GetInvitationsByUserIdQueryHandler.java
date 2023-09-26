package es.grouppayments.backend.invitations.getbyuserid;

import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.invitations._shared.domain.Invitation;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public final class GetInvitationsByUserIdQueryHandler implements QueryHandler<GetInvitationsByUserIdQuery, GetInvitationsByUserIdQueryResponse> {
    private final InvitationService invitationService;

    @Override
    public GetInvitationsByUserIdQueryResponse handle(GetInvitationsByUserIdQuery query) {
        List<Invitation> invitiations = invitationService.findByUserIdTo(query.getUserId());

        return new GetInvitationsByUserIdQueryResponse(invitiations);
    }
}
