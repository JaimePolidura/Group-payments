package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeServiceImpl;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class GetConnectedAccountLinkQueryHandler implements QueryHandler<GetConnectedAccountLinkQuery, GetConnectedAccountLinkQueryResponse> {
    private final StripeServiceImpl stripeService;
    private final PaymentUsersInfoService stripeUsersService;

    @Override
    public GetConnectedAccountLinkQueryResponse handle(GetConnectedAccountLinkQuery query) {
        String connectedAccountId = this.stripeUsersService.getByUserId(query.getUserId())
                .getConnectedAccountId();

        String link = this.stripeService.createConnectedAccountLink(connectedAccountId);

        return new GetConnectedAccountLinkQueryResponse(link);
    }
}
