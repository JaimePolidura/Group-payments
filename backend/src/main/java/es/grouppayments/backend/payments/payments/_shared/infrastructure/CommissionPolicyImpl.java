package es.grouppayments.backend.payments.payments._shared.infrastructure;

import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class CommissionPolicyImpl implements CommissionPolicy {
    private final double fee;

    public CommissionPolicyImpl(@Value("${grouppayments.fee}") double fee) {
        this.fee = fee;
    }

    @Override
    public double fee() {
        return this.fee;
    }
}
