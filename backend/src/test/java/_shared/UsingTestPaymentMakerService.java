package _shared;

import static org.junit.Assert.*;

public interface UsingTestPaymentMakerService {
    TestPaymentMaker testPaymentMaker();

    default void assertNumebrOfTimesMembersPaid(int expected){
        assertEquals(expected, testPaymentMaker().getNumebrOfTimesMembersPaid(), 0);
    }

    default void assertMoneyMembersPaidToApp(double expected){
        assertEquals(expected, testPaymentMaker().getAllMoneyMembersPaidToApp(), 0);
    }

    default void assertMoneyPaidToAdmin(double expected){
        assertEquals(expected, testPaymentMaker().getMoneyPaidToAdmin(), 0);
    }

    default void payingToAdminWillFail(){
        this.testPaymentMaker().payingToAdminWillFail();
    }

    default void payingMembersToAppWillFail(){
        this.testPaymentMaker().payingMembersToAppWillFail();;
    }

}
