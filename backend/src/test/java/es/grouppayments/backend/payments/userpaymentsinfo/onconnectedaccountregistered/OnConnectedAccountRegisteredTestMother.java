package es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountregistered;

import es.grouppayments.backend.payments.userpaymentsinfo.PaymentsUserInfoTestMother;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountRegistered;
import es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountregisterd.OnConnectedAccountRegistered;

public class OnConnectedAccountRegisteredTestMother extends PaymentsUserInfoTestMother {
    private final OnConnectedAccountRegistered eventListener;

    public OnConnectedAccountRegisteredTestMother(){
        this.eventListener = new OnConnectedAccountRegistered(
                new PaymentUsersInfoService(super.paymentUserInfoRepository())
        );
    }

    public void on(StripeConnectedAccountRegistered event){
        this.eventListener.on(event);
    }
}
