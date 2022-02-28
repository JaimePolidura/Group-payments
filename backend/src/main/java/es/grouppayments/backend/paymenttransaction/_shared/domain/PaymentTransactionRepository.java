package es.grouppayments.backend.paymenttransaction._shared.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentTransactionRepository {
    void save(PaymentTransaction transaction);

    List<PaymentTransaction> findByUserIdPayer(UUID userIdPayer);

    List<PaymentTransaction> findByUserIdPaid(UUID userIdPaid);

    Optional<PaymentTransaction> findByPaymentId(UUID paymentId);
}
