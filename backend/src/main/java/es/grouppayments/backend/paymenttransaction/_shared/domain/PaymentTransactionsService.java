package es.grouppayments.backend.paymenttransaction._shared.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentTransactionsService {
    private final PaymentTransactionRepository paymentTransactions;

    public void save(UUID userIdPaid, UUID userIdPayer, double money, String description) {
        paymentTransactions.save(new PaymentTransaction(UUID.randomUUID(), userIdPaid, userIdPayer, money, LocalDateTime.now(), description));
    }

    public List<PaymentTransaction> findByUserIdPayer(UUID userIdPayer) {
        return paymentTransactions.findByUserIdPayer(userIdPayer);
    }

    public List<PaymentTransaction> findByUserIdPaid(UUID userIdPaid) {
        return paymentTransactions.findByUserIdPaid(userIdPaid);
    }

    public Optional<PaymentTransaction> findByPaymentId(UUID paymentId) {
        return paymentTransactions.findByPaymentId(paymentId);
    }
}
