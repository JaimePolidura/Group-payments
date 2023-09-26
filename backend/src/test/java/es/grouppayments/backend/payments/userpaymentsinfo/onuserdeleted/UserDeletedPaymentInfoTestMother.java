package es.grouppayments.backend.payments.userpaymentsinfo.onuserdeleted;

import es.grouppayments.backend.payments.userpaymentsinfo.PaymentsUserInfoTestMother;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo.onuserdelted.OnUserDeletedPaymentInfo;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;

public class UserDeletedPaymentInfoTestMother extends PaymentsUserInfoTestMother {
    private final OnUserDeletedPaymentInfo eventListener;

    public UserDeletedPaymentInfoTestMother(){
        this.eventListener = new OnUserDeletedPaymentInfo(
                new PaymentUsersInfoService(super.paymentUserInfoRepository())
        );
    }

    public void on(UserDeleted event){
        this.eventListener.on(event);
    }
}
