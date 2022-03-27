package _shared;

import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;

public final class FakeCommissionPolicy implements CommissionPolicy {
    protected static final int FEE = 2;

    @Override
    public double collecteFee(double money) {
        return money * (FEE / 100);
    }
}
