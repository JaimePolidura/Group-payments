package _shared.comissions;

import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;

public final class FakeCommissionPolicy implements CommissionPolicy {
    protected static final int FEE = 50;

    @Override
    public double fee() {
        return FEE;
    }
}
