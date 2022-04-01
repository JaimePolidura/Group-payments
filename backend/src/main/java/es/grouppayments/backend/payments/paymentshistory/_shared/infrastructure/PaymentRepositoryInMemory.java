package es.grouppayments.backend.payments.paymentshistory._shared.infrastructure;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;
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
    public List<Payment> findByUserId(UUID userId) {
        return this.paymentTransactions.stream()
                .filter(isUserInPayment(userId))
                .toList();
    }

    private Predicate<? super Payment> isUserInPayment(UUID userId){
        return payment -> payment.getFromUserId().equals(userId.toString()) ||
                payment.getToUserId().equals(userId.toString());
    }

    @Override
    public Optional<Payment> findByPaymentId(UUID paymentId) {
        return paymentTransactions.stream()
                .filter(transaction -> transaction.getPaymentId().equals(paymentId))
                .findFirst();
    }
}
