package es.grouppayments.backend.groups.payment;

import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.grouppayments.backend.payments.payments._shared.domain.events.*;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

import static es.grouppayments.backend._shared.domain.Utils.*;

public final class MakePaymentTest extends MakePaymentTestMother{

    @Test
    public void shouldMakePayment(){
        final double moneyOfGroup = 20;
        UUID groupId = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        addGroup(groupId, adminUserId, moneyOfGroup, UUID.randomUUID(), UUID.randomUUID());

        execute(groupId, adminUserId);

        assertEventRaised(GroupPaymentInitialized.class, GroupMemberPayingAppDone.class, GroupPaymentDone.class, AppPayingGroupAdminDone.class);
        assertContentOfEventEquals(GroupPaymentDone.class, GroupPaymentDone::getMoneyPaidPerMember, deductFrom(moneyOfGroup, FEE));
        assertNumebrOfTimesMembersPaid(2);
        assertMoneyPaidToAdmin(deductFrom(moneyOfGroup, FEE));
        assertMoneyMembersPaidToApp(moneyOfGroup);
    }

    @Test
    public void shouldMakePaymentButPaymentToAdminWillFail(){
        final double moneyOfGroup = 20;
        UUID groupId = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        addGroup(groupId, adminUserId, moneyOfGroup, UUID.randomUUID(), UUID.randomUUID());

        payingToAdminWillFail();
        execute(groupId, adminUserId);

        assertEventRaised(GroupPaymentInitialized.class, GroupMemberPayingAppDone.class, GroupPaymentDone.class, ErrorWhilePayingToGroupAdmin.class);
        assertContentOfEventEquals(GroupPaymentDone.class, GroupPaymentDone::getMoneyPaidPerMember, deductFrom(moneyOfGroup, FEE));
        assertNumebrOfTimesMembersPaid(2);
        assertMoneyPaidToAdmin(0);
        assertMoneyMembersPaidToApp(moneyOfGroup);
    }

    @Test
    public void shouldMakePaymentButPaymentMemberToAppWillFail(){
        final double moneyOfGroup = 20;
        UUID groupId = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        addGroup(groupId, adminUserId, moneyOfGroup, UUID.randomUUID(), UUID.randomUUID());

        //Only one member will fail
        payingMembersToAppWillFail();
        execute(groupId, adminUserId);

        assertEventRaised(GroupPaymentInitialized.class, GroupMemberPayingAppDone.class, GroupPaymentDone.class, AppPayingGroupAdminDone.class, ErrorWhileGroupMemberPaying.class);
        assertContentOfEventEquals(GroupPaymentDone.class, GroupPaymentDone::getMoneyPaidPerMember, deductFrom(moneyOfGroup, FEE));
        assertNumebrOfTimesMembersPaid(1);
        assertMoneyPaidToAdmin(deductFrom(moneyOfGroup/2, FEE));
        assertMoneyMembersPaidToApp(moneyOfGroup/2);
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntMakePaymentGroupNotExists(){
        execute(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = IllegalState.class)
    public void invalidGroupState(){
        UUID groupId = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());
        changeStateTo(groupId, GroupState.PAYING);

        execute(groupId, adminUserId);
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntMakePaymentNotAdmin(){
        UUID groupId = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());

        execute(groupId, UUID.randomUUID());
    }

    @Test(expected = IllegalQuantity.class)
    public void shouldntMakePaymentNotEnoughMembers(){
        UUID groupId = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        addGroup(groupId, adminUserId);

        execute(groupId, adminUserId);
    }
}
