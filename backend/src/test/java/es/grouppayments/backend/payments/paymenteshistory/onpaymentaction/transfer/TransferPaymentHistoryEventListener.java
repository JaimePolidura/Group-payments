package es.grouppayments.backend.payments.paymenteshistory.onpaymentaction.transfer;

import es.grouppayments.backend.payments.paymenteshistory.PaymentHistoryTestMother;
import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.ErrorWhileMakingTransfer;
import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.TransferDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentContext;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer.OnErrorWhileMakingTransfer;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer.OnTransferDone;
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

        eventListener.on(new TransferDone(from, "paco", to, money, "EUR", "hola"));

        assertPaymentHistorySaved(from);
        assertContentOfPayment(from, payment -> payment.getErrorMessage() == null || payment.getErrorMessage().equals(""));
        assertContentOfPayment(from, payment -> payment.getMoney() == money);
        assertContentOfPayment(from, payment -> payment.getToUserId().equals(to));
        assertContentOfPayment(from, payment -> payment.getState() == PaymentState.SUCCESS);
        assertContentOfPayment(from, payment -> payment.getContext() == PaymentContext.TRANSFERENCE);

        assertPaymentHistorySaved(to);
        assertContentOfPayment(to, payment -> payment.getErrorMessage() == null || payment.getErrorMessage().equals(""));
        assertContentOfPayment(to, payment -> payment.getMoney() == money);
        assertContentOfPayment(to, payment -> payment.getFromUserId().equals(from));
        assertContentOfPayment(to, payment -> payment.getState() == PaymentState.SUCCESS);
        assertContentOfPayment(to, payment -> payment.getContext() == PaymentContext.TRANSFERENCE);
    }

    @Test
    public void shouldSaveTransferFatalError(){
        final OnErrorWhileMakingTransfer eventListener = new OnErrorWhileMakingTransfer(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );
        UUID userIdFrom = UUID.randomUUID();

        eventListener.on(new ErrorWhileMakingTransfer(userIdFrom, "paco", UUID.randomUUID(),10,
                "EUR", "hola", "error"));

        assertPaymentHistorySaved(userIdFrom);
        assertContentOfPayment(userIdFrom, payment -> payment.getErrorMessage().equals("error"));
        assertContentOfPayment(userIdFrom, payment -> payment.getMoney() == 10);
        assertContentOfPayment(userIdFrom, payment -> payment.getState() == PaymentState.ERROR);
        assertContentOfPayment(userIdFrom, payment -> payment.getContext() == PaymentContext.TRANSFERENCE);
        assertContentOfPayment(userIdFrom, payment -> payment.getFromUserId().equals(userIdFrom));
    }
}
