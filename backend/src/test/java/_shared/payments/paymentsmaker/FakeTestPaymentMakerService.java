package _shared.payments.paymentsmaker;

import _shared.comissions.UsingCommissionPolicy;
import es.grouppayments.backend.users.users._shared.domain.Currency;

import java.util.UUID;

public final class FakeTestPaymentMakerService implements TestPaymentMaker, UsingCommissionPolicy {
    private double allMoneyPaid;
    private int numebrOfTimesMembersPaid;
    private boolean willFail;

    @Override
    public String makePayment(UUID fromUserId, UUID toUserId, double money, Currency currency) throws Exception {
        if(willFail){
            this.willFail = false;
            throw new Exception();
        }

        this.numebrOfTimesMembersPaid++;
        this.allMoneyPaid += commissionPolicy().deductFromFee(money);

        return null;
    }

    @Override
    public double getAllMoneyPaid() {
        return this.allMoneyPaid;
    }

    @Override
    public int getNumebrOfTimesMembersPaid() {
        return this.numebrOfTimesMembersPaid;
    }

    @Override
    public void willFail() {
        this.willFail = true;
    }
}
