package es.grouppayments.backend.groups.makepayment;

import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.payments._shared.domain.PaymentDone;
import es.grouppayments.backend.payments._shared.domain.UnprocessablePayment;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;


public class MakePaymentTest extends MakePaymentMother{
    @Test
    public void shouldMakePayment(){
        final int numberOfMembersNotAdmin = 2;
        final double moneyOfGroup = 60;
        final double moneyPerMember = moneyOfGroup / numberOfMembersNotAdmin;
        UUID groupId = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        addGroup(groupId, userID, moneyOfGroup, UUID.randomUUID(), UUID.randomUUID());

        makePayment(groupId, userID);

        assertEventRaised(PaymentDone.class, GroupDeleted.class);
        assertGroupDeleted(groupId);
        assertContentOfEventEquals(PaymentDone.class, PaymentDone::getMoneyPaidPerMember, moneyPerMember);
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntMakePaymentGroupNotExists(){
        makePayment(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = IllegalQuantity.class)
    public void shouldntMakePaymentGroupOnlyOneMember(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId, 10);

        makePayment(groupId, userId);
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntMakePaymentNotTheAdmin(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID(), 10, UUID.randomUUID());

        makePayment(groupId, UUID.randomUUID());
    }

    @Test(expected = UnprocessablePayment.class)
    public void shouldntMakePaymentUnprocessable(){
        UUID groupId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        addGroup(groupId, userId, 10);
        addMember(groupId, UUID.randomUUID());
        addMember(groupId, UUID.randomUUID());

        super.willFail();

        makePayment(groupId, userId);
    }

    @Test(expected = IllegalState.class)
    public void shouldntMakePaymentInvalidState(){
        UUID groupId = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        addGroup(groupId, userID, 10, UUID.randomUUID(), UUID.randomUUID());
        changeStateTo(groupId, GroupState.PAYING);

        makePayment(groupId, userID);
    }
}
