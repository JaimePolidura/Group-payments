package es.grouppayments.backend.payments._shared.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentHistoricalService {
    private final PaymentRepository paymentTransactions;

    public void save(UUID userIdPaid, UUID userIdPayer, double money, String description) {
        paymentTransactions.save(new Payment(UUID.randomUUID(), userIdPaid, userIdPayer, money, LocalDateTime.now(), description));
    }

    public List<Payment> findByUserIdPayer(UUID userIdPayer) {
        return paymentTransactions.findByUserIdPayer(userIdPayer);
    }

    public List<Payment> findByUserIdPaid(UUID userIdPaid) {
        return paymentTransactions.findByUserIdPaid(userIdPaid);
    }

    public Optional<Payment> findByPaymentId(UUID paymentId) {
        return paymentTransactions.findByPaymentId(paymentId);
    }
}
