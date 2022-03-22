package _shared;

import es.grouppayments.backend.payments.payments._shared.domain.PaymentMakerService;

public interface TestPaymentMaker extends PaymentMakerService {
    double getMoneyPaidToAdmin();
    double getAllMoneyMembersPaidToApp();
    int getNumebrOfTimesMembersPaid();
    void payingToAdminWillFail();
    void payingMembersToAppWillFail();
}
