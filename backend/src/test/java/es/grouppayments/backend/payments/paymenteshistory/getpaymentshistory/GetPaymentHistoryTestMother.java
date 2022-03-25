package es.grouppayments.backend.payments.paymenteshistory.getpaymentshistory;

import es.grouppayments.backend.payments.paymenteshistory.onpaymentaction.PaymentHistoryTestMother;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory.getpaymentshistory.GetPaymentsHistoryQuery;
import es.grouppayments.backend.payments.paymentshistory.getpaymentshistory.GetPaymentsHistoryQueryHandler;
import es.grouppayments.backend.payments.paymentshistory.getpaymentshistory.GetPaymentsHistoryQueryResponse;

public class GetPaymentHistoryTestMother extends PaymentHistoryTestMother {
    private final GetPaymentsHistoryQueryHandler queryHandler;

    public GetPaymentHistoryTestMother(){
        this.queryHandler = new GetPaymentsHistoryQueryHandler(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );
    }

    public GetPaymentsHistoryQueryResponse execute(GetPaymentsHistoryQuery query){
        return this.queryHandler.handle(query);
    }
}
