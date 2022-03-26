package _shared;

import java.util.UUID;

public final class FakeTestPaymentMakerService implements TestPaymentMaker {
    private double moneyPaidToAdmin;
    private double allMoneyMembersPaidToApp;
    private int numebrOfTimesMembersPaid;
    private boolean paymentToAdminWillFail;
    private boolean paymentMembersToAppWillFail;

    @Override
    public String paymentMemberToApp(UUID userId, double money, String currencyCode) throws Exception {
        if(paymentMembersToAppWillFail){
            this.paymentMembersToAppWillFail = false;
            throw new Exception();
        }

        this.numebrOfTimesMembersPaid++;
        this.allMoneyMembersPaidToApp += money;

        return null;
    }

    @Override
    public String paymentAppToAdmin(UUID userId, double money, String currencyCode) throws Exception {
        if(paymentToAdminWillFail){
            this.paymentToAdminWillFail = false;
            throw new Exception();
        }

        this.moneyPaidToAdmin =+ money;

        return null;
    }

    @Override
    public double getMoneyPaidToAdmin() {
        return this.moneyPaidToAdmin;
    }

    @Override
    public double getAllMoneyMembersPaidToApp() {
        return this.allMoneyMembersPaidToApp;
    }

    @Override
    public int getNumebrOfTimesMembersPaid() {
        return this.numebrOfTimesMembersPaid;
    }

    @Override
    public void payingToAdminWillFail() {
        this.paymentToAdminWillFail = true;
    }

    @Override
    public void payingMembersToAppWillFail() {
        this.paymentMembersToAppWillFail = true;
    }
}
