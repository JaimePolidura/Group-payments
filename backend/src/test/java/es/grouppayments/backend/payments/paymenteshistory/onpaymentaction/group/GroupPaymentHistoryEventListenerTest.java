package es.grouppayments.backend.payments.paymenteshistory.onpaymentaction.group;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.grouppayments.backend.payments.paymenteshistory.PaymentHistoryTestMother;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.onapppayingadmindone.OnAppPayingAdminDone;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.onerrorwhilememberpaying.OnErrorWhileMemberPaying;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.onerrorwhilepayingtoadmin.OnErrorWhilePayingToAdmin;
import es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.onmemberpayingappdone.OnMemberPayingAppDone;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public final class GroupPaymentHistoryEventListenerTest extends PaymentHistoryTestMother {
    @Test
    public void shouldSaveOnAppPayingAdminDone(){
        UUID paidUserId = UUID.randomUUID(); //Admin of group
        OnAppPayingAdminDone eventListener = new OnAppPayingAdminDone(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );

        Group group = new Group(UUID.randomUUID(), "1", LocalDateTime.now(), 10, paidUserId, GroupState.PAYING);

        eventListener.on(new AppPayingGroupAdminDone(group, 10, "EUR", "", paidUserId));

        assertPaymentHistorySaved(paidUserId);
        assertContentOfPayment(paidUserId, payment -> payment.getErrorMessage() == null || payment.getErrorMessage().equals(""));
        assertContentOfPayment(paidUserId, payment -> payment.getMoney() == 10);
        assertContentOfPayment(paidUserId, payment -> payment.getPayer().equals("APP"));
        assertContentOfPayment(paidUserId, payment -> payment.getState() == PaymentState.SUCCESS);
        assertContentOfPayment(paidUserId, payment -> payment.getType() == PaymentType.APP_TO_USER);
    }

    @Test
    public void shouldSaveOnErrorWhileMemberPaying(){
        UUID payerdUserIdMember = UUID.randomUUID(); //Admin of group
        OnErrorWhileMemberPaying eventListener = new OnErrorWhileMemberPaying(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );

        Group group = new Group(UUID.randomUUID(), "", LocalDateTime.now(), 10, UUID.randomUUID(), GroupState.PAYING);

        eventListener.on(new ErrorWhileGroupMemberPaying(group, 10, "EUR", "", payerdUserIdMember, "error"));

        assertPaymentHistorySaved(payerdUserIdMember);
        assertContentOfPayment(payerdUserIdMember, payment -> payment.getErrorMessage().equals("error"));
        assertContentOfPayment(payerdUserIdMember, payment -> payment.getMoney() == 10);
        assertContentOfPayment(payerdUserIdMember, payment -> payment.getPayer().equals(payerdUserIdMember.toString()));
        assertContentOfPayment(payerdUserIdMember, payment -> payment.getState() == PaymentState.ERROR);
        assertContentOfPayment(payerdUserIdMember, payment -> payment.getType() == PaymentType.USER_TO_APP);
        assertContentOfPayment(payerdUserIdMember, payment -> payment.getPaid().equalsIgnoreCase("APP"));
    }

    @Test
    public void shouldSaveOnErrorWhilePayingToAdmin(){
        UUID paidUserIdMember = UUID.randomUUID(); //Admin of group
        OnErrorWhilePayingToAdmin eventListener = new OnErrorWhilePayingToAdmin(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );

        Group group = new Group(UUID.randomUUID(), "", LocalDateTime.now(), 20, paidUserIdMember, GroupState.PAYING);

        eventListener.on(new ErrorWhilePayingToGroupAdmin(20, "EUR", "", paidUserIdMember, "error", group));

        assertPaymentHistorySaved(paidUserIdMember);
        assertContentOfPayment(paidUserIdMember, payment -> payment.getErrorMessage().equals("error"));
        assertContentOfPayment(paidUserIdMember, payment -> payment.getMoney() == 20);
        assertContentOfPayment(paidUserIdMember, payment -> payment.getPayer().equals("APP"));
        assertContentOfPayment(paidUserIdMember, payment -> payment.getState() == PaymentState.ERROR);
        assertContentOfPayment(paidUserIdMember, payment -> payment.getType() == PaymentType.APP_TO_USER);
        assertContentOfPayment(paidUserIdMember, payment -> payment.getPaid().equalsIgnoreCase(paidUserIdMember.toString()));
    }

    @Test
    public void shouldSaveOnMemberPayingAppDone(){
        UUID payerUserIdMember = UUID.randomUUID(); //Admin of group
        OnMemberPayingAppDone eventListener = new OnMemberPayingAppDone(
                new PaymentHistoryService(super.paymentsHistoryRepository())
        );

        Group group = new Group(UUID.randomUUID(), "", LocalDateTime.now(), 30, UUID.randomUUID(), GroupState.PAYING);

        eventListener.on(new GroupMemberPayingAppDone(group, 30, "EUR", "", payerUserIdMember));

        assertPaymentHistorySaved(payerUserIdMember);
        assertContentOfPayment(payerUserIdMember, payment -> payment.getMoney() == 30);
        assertContentOfPayment(payerUserIdMember, payment -> payment.getPaid().equals("APP"));
        assertContentOfPayment(payerUserIdMember, payment -> payment.getState() == PaymentState.SUCCESS);
        assertContentOfPayment(payerUserIdMember, payment -> payment.getType() == PaymentType.USER_TO_APP);
        assertContentOfPayment(payerUserIdMember, payment -> payment.getPayer().equalsIgnoreCase(payerUserIdMember.toString()));
    }
}
