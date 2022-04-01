package es.grouppayments.backend.payments.payments.transfer;

import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.*;
import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.porro.TransferDone;
import es.grouppayments.backend.users._shared.domain.UserState;
import es.jaime.javaddd.domain.exceptions.*;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertThrows;

public final class TransferTest extends TransferTestMother{
    private static final int MONEY = 10;

    @Test
    public void shouldMakeTransferPaymentAppToUserFail(){
        UUID userIdTo = UUID.randomUUID();
        UUID userIdFrom = UUID.randomUUID();
        addUser(userIdFrom, userIdTo);

        super.payingTAppToUserWillFail();
        execute(userIdFrom, userIdTo, MONEY);

        assertEventRaised(TransferUserPaidToApp.class, TransferErrorAppPaidToUser.class, TransferRolledBack.class);
        assertMoneyUsersPaidToApp(MONEY);
        assertMoneyAppPaidToUser(MONEY);
    }

    @Test
    public void shouldMakeTransfer(){
        UUID userIdTo = UUID.randomUUID();
        UUID userIdFrom = UUID.randomUUID();
        addUser(userIdFrom, userIdTo);

        execute(userIdFrom, userIdTo, MONEY);

        assertEventRaised();

        assertEventRaised(TransferUserPaidToApp.class, TransferDone.class);
        assertMoneyUsersPaidToApp(MONEY);
        assertMoneyAppPaidToUser(super.commissionPolicy().deductCommission(MONEY));
    }

    @Test
    public void shoulndtdMakeTransferPaymentUserToAppFail(){
        UUID userIdTo = UUID.randomUUID();
        UUID userIdFrom = UUID.randomUUID();
        addUser(userIdFrom, userIdTo);

        super.payingUserToAppWillFail();
        execute(userIdFrom, userIdTo, MONEY);

        assertEventRaised(TransferErrorUserPaidToApp.class);
        assertMoneyUsersPaidToApp(0);
        assertMoneyAppPaidToUser(0);
    }

    @Test(expected = CannotBeYourself.class)
    public void shouldntTransferSameUser(){
        UUID userId = UUID.randomUUID();
        execute(userId, userId, 10);
    }

    @Test
    public void shouldntTransferIllegalDesc(){
        assertThrows(IllegalLength.class, () -> execute(UUID.randomUUID(), UUID.randomUUID(), MONEY, ""));
        assertThrows(IllegalLength.class, () -> execute(UUID.randomUUID(), UUID.randomUUID(), MONEY, "a".repeat(17)));
    }

    @Test
    public void shouldntTransferIllegalMoney(){
        assertThrows(IllegalQuantity.class, () -> execute(UUID.randomUUID(), UUID.randomUUID(), 0, "a"));
        assertThrows(IllegalQuantity.class, () -> execute(UUID.randomUUID(), UUID.randomUUID(), -1, "a"));
        assertThrows(IllegalQuantity.class, () -> execute(UUID.randomUUID(), UUID.randomUUID(), 10001, "a"));
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntTransferUserNotExists(){
        UUID userIdFrom = UUID.randomUUID();
        addUser(userIdFrom);

        execute(userIdFrom, UUID.randomUUID(), MONEY);
    }

    @Test(expected = IllegalState.class)
    public void shouldntTransferInvalidUserState(){
        UUID userIdTo = UUID.randomUUID();
        addUser(userIdTo, UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED);

        execute(UUID.randomUUID(), userIdTo, MONEY);
    }
}
