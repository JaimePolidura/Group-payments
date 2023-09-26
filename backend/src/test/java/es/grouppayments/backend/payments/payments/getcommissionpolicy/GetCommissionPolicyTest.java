package es.grouppayments.backend.payments.payments.getcommissionpolicy;

import org.junit.Test;

import static org.junit.Assert.*;

public final class GetCommissionPolicyTest extends GetCommissionPolicyTestMother{
    @Test
    public void shouldGet(){
        double fee = super.execute(new GetCommissionPolicyQuery())
                .getCommission();

        assertEquals(super.commissionPolicy().fee(), fee, 0L);
    }
}
