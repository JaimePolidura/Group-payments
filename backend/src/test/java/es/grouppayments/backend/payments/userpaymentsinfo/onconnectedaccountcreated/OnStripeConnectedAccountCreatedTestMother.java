package es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountcreated;

import es.grouppayments.backend.payments.userpaymentsinfo.PaymentsUserInfoTestMother;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountCreated;

public class OnStripeConnectedAccountCreatedTestMother extends PaymentsUserInfoTestMother {
    private final OnStripeConnectedAccountCreated eventListener;

    public OnStripeConnectedAccountCreatedTestMother(){
        this.eventListener = new OnStripeConnectedAccountCreated(
                new PaymentUsersInfoService(super.paymentUserInfoRepository())
        );
    }

    public void on(StripeConnectedAccountCreated event){
        this.eventListener.on(event);
    }
}
