package es.grouppayments.backend.payments.payments.getcommissionpolicy;

import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class GetCommissionPolicyQueryHandler implements QueryHandler<GetCommissionPolicyQuery, GetCommissionPolicyQueryResponse> {
    private final CommissionPolicy commissionPolicy;

    @Override
    public GetCommissionPolicyQueryResponse handle(GetCommissionPolicyQuery getCommissionPolicyQuery) {
        return new GetCommissionPolicyQueryResponse(this.commissionPolicy.fee());
    }
}
