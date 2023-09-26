package es.grouppayments.backend.payments.paymenteshistory.onpaymentaction.group;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.grouppayments.backend.payments.paymenteshistory.PaymentHistoryTestMother;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.ErrorWhilePayingToGroupAdmin;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.MemberPaidToAdmin;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentContext;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.OnErrorWhileMemberPayingToAdmin;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.OnMemberPaidToAdmin;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public final class GroupPaymentHistoryEventListenerTest extends PaymentHistoryTestMother {
    @Test
    public void shouldSaveOnAppPayingAdminDone(){
        UUID toUserId = UUID.randomUUID(); //Admin of group
        OnMemberPaidToAdmin eventListener = new OnMemberPaidToAdmin(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );

        Group group = new Group(UUID.randomUUID(), "1", LocalDateTime.now(), 10, toUserId, GroupState.PAYING, Currency.EUR);

        eventListener.on(new MemberPaidToAdmin(group, 10, Currency.EUR, "", toUserId));

        assertPaymentHistorySaved(toUserId);
        assertContentOfPayment(toUserId, payment -> payment.getErrorMessage() == null || payment.getErrorMessage().equals(""));
        assertContentOfPayment(toUserId, payment -> payment.getMoney() == 10);
        assertContentOfPayment(toUserId, payment -> payment.getToUserId().equals(toUserId));
        assertContentOfPayment(toUserId, payment -> payment.getState() == PaymentState.SUCCESS);
        assertContentOfPayment(toUserId, payment -> payment.getContext() == PaymentContext.GROUP_PAYMENT);
    }

    @Test
    public void shouldSaveOnErrorWhileMemberPaying(){
        UUID userIdMember = UUID.randomUUID();
        UUID userIdAdmin = UUID.randomUUID();
        OnErrorWhileMemberPayingToAdmin eventListener = new OnErrorWhileMemberPayingToAdmin(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );

        Group group = new Group(UUID.randomUUID(), "", LocalDateTime.now(), 10, userIdAdmin, GroupState.PAYING, Currency.EUR);

        eventListener.on(new ErrorWhilePayingToGroupAdmin(10, Currency.EUR, "payment", userIdMember, "error", group));

        assertPaymentHistorySaved(userIdMember);
        assertContentOfPayment(userIdMember, payment -> payment.getErrorMessage().equals("error"));
        assertContentOfPayment(userIdMember, payment -> payment.getMoney() == 10);
        assertContentOfPayment(userIdMember, payment -> payment.getFromUserId().equals(userIdMember));
        assertContentOfPayment(userIdMember, payment -> payment.getState() == PaymentState.ERROR);
        assertContentOfPayment(userIdMember, payment -> payment.getContext() == PaymentContext.GROUP_PAYMENT);
        assertContentOfPayment(userIdMember, payment -> payment.getToUserId().equals(userIdAdmin));
    }
}
