package es.grouppayments.backend.payments.paymentshistory._shared.infrastructure;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PaymentRepositoryInMemory implements PaymentsHistoryRepository {
    private final Set<Payment> paymentTransactions;

    public PaymentRepositoryInMemory(){
        this.paymentTransactions = new HashSet<>();
    }

    @Override
    public void save(Payment transaction) {
        this.paymentTransactions.add(transaction);
    }

    @Override
    public List<Payment> findByUserIdPayer(UUID userIdPayer) {
        return paymentTransactions.stream()
                .filter(transaction -> transaction.getPayer().equals(userIdPayer))
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findByUserIdPaid(UUID userIdPaid) {
        return paymentTransactions.stream()
                .filter(transaction -> transaction.getPaid().equals(userIdPaid))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Payment> findByPaymentId(UUID paymentId) {
        return paymentTransactions.stream()
                .filter(transaction -> transaction.getPaymentId().equals(paymentId))
                .findFirst();
    }
}
