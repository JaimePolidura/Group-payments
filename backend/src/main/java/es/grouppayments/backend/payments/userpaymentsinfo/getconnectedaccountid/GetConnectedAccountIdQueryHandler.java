package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountid;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.StripeUsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class GetConnectedAccountIdQueryHandler implements QueryHandler<GetConnectedAccountIdQuery, GetConnectedAccountIdQueryResponse> {
    private final StripeUsersService stripeUsersService;

    @Override
    public GetConnectedAccountIdQueryResponse handle(GetConnectedAccountIdQuery query) {
        return new GetConnectedAccountIdQueryResponse(this.stripeUsersService.getdByUserId(query.getUserId())
                .getConnectedAccountId());
    }
}
