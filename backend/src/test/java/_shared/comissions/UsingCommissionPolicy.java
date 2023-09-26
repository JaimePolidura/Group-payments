package _shared.comissions;

import _shared.comissions.FakeCommissionPolicy;
import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;

public interface UsingCommissionPolicy {
    default CommissionPolicy commissionPolicy() {
        return new FakeCommissionPolicy();
    }
}
