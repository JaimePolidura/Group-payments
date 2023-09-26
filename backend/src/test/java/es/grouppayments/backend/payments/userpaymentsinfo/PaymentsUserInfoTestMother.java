package es.grouppayments.backend.payments.userpaymentsinfo;

import _shared.TestMother;
import _shared.payments.paymentsuserinfo.UsingPaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfoRepository;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.PaymentUserInfoRepositoryInMemory;

public class PaymentsUserInfoTestMother extends TestMother implements UsingPaymentUserInfo {
    protected final PaymentUserInfoRepository repository;

    public PaymentsUserInfoTestMother(){
        this.repository = new PaymentUserInfoRepositoryInMemory();
    }

    @Override
    public PaymentUserInfoRepository paymentUserInfoRepository() {
        return this.repository;
    }
}
