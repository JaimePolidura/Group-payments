package _shared;

import static org.junit.Assert.*;

public interface UsingTestPaymentMakerService {
    TestPaymentMaker testPaymentMaker();

    default void assertNumebrOfTimesMembersPaid(int expected){
        assertEquals(expected, testPaymentMaker().getNumebrOfTimesMembersPaid(), 0);
    }

    default void assertMoneyUsersPaidToApp(double expected){
        assertEquals(expected, testPaymentMaker().getAllMoneyMembersPaidToApp(), 0);
    }

    default void assertMoneyAppPaidToUser(double expected){
        assertEquals(expected, testPaymentMaker().getMoneyPaidToAdmin(), 0);
    }

    default void payingTAppToUserWillFail(){
        this.testPaymentMaker().payingToAdminWillFail();
    }

    default void payingUserToAppWillFail(){
        this.testPaymentMaker().payingMembersToAppWillFail();;
    }

}
