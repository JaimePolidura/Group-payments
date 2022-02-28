package es.grouppayments.backend.paymenttransaction._shared.infrastructure;

import es.grouppayments.backend.paymenttransaction._shared.domain.PaymentTransaction;
import es.grouppayments.backend.paymenttransaction._shared.domain.PaymentTransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PaymentTransactionRepositoryInMemory implements PaymentTransactionRepository {
    private final Set<PaymentTransaction> paymentTransactions;

    public PaymentTransactionRepositoryInMemory(){
        this.paymentTransactions = new HashSet<>();
    }

    @Override
    public void save(PaymentTransaction transaction) {
        this.paymentTransactions.add(transaction);
    }

    @Override
    public List<PaymentTransaction> findByUserIdPayer(UUID userIdPayer) {
        return paymentTransactions.stream()
                .filter(transaction -> transaction.getUserIdPayer().equals(userIdPayer))
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentTransaction> findByUserIdPaid(UUID userIdPaid) {
        return paymentTransactions.stream()
                .filter(transaction -> transaction.getUserIdPaid().equals(userIdPaid))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentTransaction> findByPaymentId(UUID paymentId) {
        return paymentTransactions.stream()
                .filter(transaction -> transaction.getPaymentId().equals(paymentId))
                .findFirst();
    }
}
