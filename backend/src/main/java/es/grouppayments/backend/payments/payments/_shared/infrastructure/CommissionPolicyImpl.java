package es.grouppayments.backend.payments.payments._shared.infrastructure;

import es.grouppayments.backend.payments.payments._shared.domain.ComimssionPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class CommissionPolicyImpl implements ComimssionPolicy {
    private final double fee;

    public CommissionPolicyImpl(@Value("${grouppayments.fee}") double fee) {
        this.fee = fee;
    }

    @Override
    public double collecteFee(double money) {
        return (fee / 100) * money;
    }
}
