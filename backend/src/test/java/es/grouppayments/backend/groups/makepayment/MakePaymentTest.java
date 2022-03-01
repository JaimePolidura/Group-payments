package es.grouppayments.backend.groups.makepayment;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.payments._shared.domain.PaymentDone;
import es.grouppayments.backend.payments._shared.domain.UnprocessablePayment;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
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
        addGroup(groupId, UUID.randomUUID(), moneyOfGroup, UUID.randomUUID(), UUID.randomUUID());

        makePayment(groupId);

        assertEventRaised(PaymentDone.class, GroupDeleted.class);
        assertGroupDeleted(groupId);
        assertContentOfEventEquals(PaymentDone.class, PaymentDone::getMoneyPaidPerUser, moneyPerMember);
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntMakePaymentGroupNotExists(){
        makePayment(UUID.randomUUID());
    }

    @Test(expected = IllegalQuantity.class)
    public void shouldntMakePaymentGroupOnlyOneMember(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID(), 10);

        makePayment(groupId);
    }

    @Test(expected = UnprocessablePayment.class)
    public void shouldntMakePaymentUnprocessable(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID(), 10);
        addMember(groupId, UUID.randomUUID());
        addMember(groupId, UUID.randomUUID());

        super.willFail();

        makePayment(groupId);
    }
}
