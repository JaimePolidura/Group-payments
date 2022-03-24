package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.StripeUsersService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class GetConnectedAccountLinkQueryHandler implements QueryHandler<GetConnectedAccountLinkQuery, GetConnectedAccountLinkQueryResponse> {
    private final StripeService stripeService;
    private final StripeUsersService stripeUsersService;

    @Override
    public GetConnectedAccountLinkQueryResponse handle(GetConnectedAccountLinkQuery query) {
        String connectedAccountId = this.stripeUsersService.getdByUserId(query.getUserId())
                .getConnectedAccountId();

         String link = this.stripeService.createConnectedAccountLink(connectedAccountId);

        return new GetConnectedAccountLinkQueryResponse(link);
    }
}
