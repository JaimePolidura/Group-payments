package es.grouppayments.backend.payments.paymentshistory._shared.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentsHistoryRepository {
    void save(Payment transaction);

    List<Payment> findByUserId(UUID userId);

    Optional<Payment> findByPaymentId(UUID paymentId);
}
