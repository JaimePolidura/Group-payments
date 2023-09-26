package es.grouppayments.backend.payments.payments.getcommissionpolicy;

import es.grouppayments.backend.payments.payments.PaymentsTestMother;

public class GetCommissionPolicyTestMother extends PaymentsTestMother {
    private final GetCommissionPolicyQueryHandler handler;

    public GetCommissionPolicyTestMother() {
        this.handler = new GetCommissionPolicyQueryHandler(super.commissionPolicy());
    }

    public GetCommissionPolicyQueryResponse execute(GetCommissionPolicyQuery query){
        return this.handler.handle(query);
    }
}
