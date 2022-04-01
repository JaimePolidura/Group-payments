package _shared;

import java.util.UUID;

public final class FakeTestPaymentMakerService implements TestPaymentMaker {
    private double allMoneyPaid;
    private int numebrOfTimesMembersPaid;
    private boolean willFail;

    @Override
    public String makePayment(UUID fromUserId, UUID toUserId, double money, String currencyCode) throws Exception {
        if(willFail){
            this.willFail = false;
            throw new Exception();
        }

        this.numebrOfTimesMembersPaid++;
        this.allMoneyPaid += money;

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
