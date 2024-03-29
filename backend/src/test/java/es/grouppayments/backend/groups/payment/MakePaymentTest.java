package es.grouppayments.backend.groups.payment;

import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.ErrorWhilePayingToGroupAdmin;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.GroupPaymentDone;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.GroupPaymentInitialized;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.MemberPaidToAdmin;
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

        assertEventRaised(GroupPaymentInitialized.class, MemberPaidToAdmin.class, GroupPaymentDone.class, GroupPaymentDone.class);
        assertContentOfEventEquals(GroupPaymentDone.class, GroupPaymentDone::getMoneyPaidPerMember, deductFrom(moneyOfGroup, FEE));
        assertNumebrOfTimesMembersPaid(2);
        assertMoneyPaid(super.commissionPolicy().deductFromFee(moneyOfGroup));
    }

    @Test
    public void shouldMakePaymentButPaymentWillFail(){
        final double moneyOfGroup = 20;
        UUID groupId = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        addGroup(groupId, adminUserId, moneyOfGroup, UUID.randomUUID(), UUID.randomUUID());

        super.willFail();
        execute(groupId, adminUserId);

        assertEventRaised(GroupPaymentInitialized.class, GroupPaymentDone.class, ErrorWhilePayingToGroupAdmin.class);
        assertContentOfEventEquals(GroupPaymentDone.class, GroupPaymentDone::getMoneyPaidPerMember, deductFrom(moneyOfGroup, FEE));
        assertNumebrOfTimesMembersPaid(1);
        //Two members to pay, one will fail
        assertMoneyPaid(super.commissionPolicy().deductFromFee(moneyOfGroup/2));
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
