package es.grouppayments.backend.payments.userpaymentsinfo.oncustomercreated;

import es.grouppayments.backend.payments.userpaymentsinfo.PaymentsUserInfoTestMother;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeCustomerCreated;

public class OnCustomerCreatedTestMother extends PaymentsUserInfoTestMother {
    private final OnStripeCustomerCreated onStripeCustomerCreated;

    public OnCustomerCreatedTestMother(){
        this.onStripeCustomerCreated = new OnStripeCustomerCreated(
                new PaymentUsersInfoService(super.paymentUserInfoRepository())
        );
    }

    public void on(StripeCustomerCreated event){
        this.onStripeCustomerCreated.on(event);
    }
}
