package es.grouppayments.backend.payments._shared.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    void save(Payment transaction);

    List<Payment> findByUserIdPayer(UUID userIdPayer);

    List<Payment> findByUserIdPaid(UUID userIdPaid);

    Optional<Payment> findByPaymentId(UUID paymentId);
}
