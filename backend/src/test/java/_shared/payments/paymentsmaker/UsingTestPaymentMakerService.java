package _shared.payments.paymentsmaker;

import static org.junit.Assert.*;

public interface UsingTestPaymentMakerService {
    TestPaymentMaker testPaymentMaker();

    default void assertNumebrOfTimesMembersPaid(int expected){
        assertEquals(expected, testPaymentMaker().getNumebrOfTimesMembersPaid(), 0);
    }

    default void assertMoneyPaid(double exepected){
        assertEquals(exepected, testPaymentMaker().getAllMoneyPaid(), 0);
    }

    default void willFail() {
        this.testPaymentMaker().willFail();
    }

}
