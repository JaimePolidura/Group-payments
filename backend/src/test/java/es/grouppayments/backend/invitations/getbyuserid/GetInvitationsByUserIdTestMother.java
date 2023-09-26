package es.grouppayments.backend.invitations.getbyuserid;

import es.grouppayments.backend.invitations.InvitationTestMother;
import es.grouppayments.backend.invitations._shared.application.InvitationService;

public class GetInvitationsByUserIdTestMother extends InvitationTestMother {
    private final GetInvitationsByUserIdQueryHandler queryHandler;

    public GetInvitationsByUserIdTestMother(){
        this.queryHandler = new GetInvitationsByUserIdQueryHandler(
                new InvitationService(super.invitationsRepository(), super.testEventBus)
        );
    }

    public GetInvitationsByUserIdQueryResponse execute(GetInvitationsByUserIdQuery query){
        return this.queryHandler.handle(query);
    }
}
