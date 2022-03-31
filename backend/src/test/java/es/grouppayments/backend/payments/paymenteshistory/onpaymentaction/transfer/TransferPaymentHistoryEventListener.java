package es.grouppayments.backend.payments.paymenteshistory.onpaymentaction.transfer;

import es.grouppayments.backend.payments.paymenteshistory.PaymentHistoryTestMother;
import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.TransferFatalErrorRollingback;
import es.grouppayments.backend.payments.payments.transfer.TransferDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer.onfatalerrorrollingback.OnTransferFatalErrorRollingback;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer.ontransferdone.OnTransferDone;
import org.junit.Test;

import java.util.UUID;

public final class TransferPaymentHistoryEventListener extends PaymentHistoryTestMother {
    @Test
    public void shouldSaveOnTransferDone(){
        final OnTransferDone eventListener = new OnTransferDone(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();
        int money = 10;

        eventListener.on(new TransferDone(from, "paco", to, money, money, "EUR", "hola"));

        assertPaymentHistorySaved(from);
        assertContentOfPayment(from, payment -> payment.getErrorMessage() == null || payment.getErrorMessage().equals(""));
        assertContentOfPayment(from, payment -> payment.getMoney() == money);
        assertContentOfPayment(from, payment -> payment.getPaid().equals("APP"));
        assertContentOfPayment(from, payment -> payment.getState() == PaymentState.SUCCESS);
        assertContentOfPayment(from, payment -> payment.getType() == PaymentType.USER_TO_APP);

        assertPaymentHistorySaved(to);
        assertContentOfPayment(to, payment -> payment.getErrorMessage() == null || payment.getErrorMessage().equals(""));
        assertContentOfPayment(to, payment -> payment.getMoney() == money);
        assertContentOfPayment(to, payment -> payment.getPayer().equals("APP"));
        assertContentOfPayment(to, payment -> payment.getState() == PaymentState.SUCCESS);
        assertContentOfPayment(to, payment -> payment.getType() == PaymentType.APP_TO_USER);
    }

    @Test
    public void shouldSaveTransferFatalError(){
        final OnTransferFatalErrorRollingback eventListener = new OnTransferFatalErrorRollingback(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );
        UUID userIdFrom = UUID.randomUUID();

        eventListener.on(new TransferFatalErrorRollingback(userIdFrom, "error", 10, "EUR", "hola"));

        assertPaymentHistorySaved(userIdFrom);
        assertContentOfPayment(userIdFrom, payment -> payment.getErrorMessage().equals("error"));
        assertContentOfPayment(userIdFrom, payment -> payment.getMoney() == 10);
        assertContentOfPayment(userIdFrom, payment -> payment.getState() == PaymentState.ERROR);
        assertContentOfPayment(userIdFrom, payment -> payment.getType() == PaymentType.APP_TO_USER);
        assertContentOfPayment(userIdFrom, payment -> payment.getPayer().equals("APP"));
    }
}
