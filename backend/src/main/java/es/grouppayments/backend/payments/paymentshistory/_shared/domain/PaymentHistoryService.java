package es.grouppayments.backend.payments.paymentshistory._shared.domain;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentHistoryService {
    private final PaymentsHistoryRepository paymentsHistory;

    public void save(UUID fromUserId, UUID toUserId, Currency currnecy, double money, String description, PaymentContext type){
        this.paymentsHistory.save(new Payment(UUID.randomUUID(), fromUserId, toUserId, money, currnecy, LocalDateTime.now(),
                description, PaymentState.SUCCESS, type, null));
    }

    public void save(UUID fromUserId, UUID toUserId, Currency currnecy, double money, String description, PaymentContext type,
                     String errorMessage){
        this.paymentsHistory.save(new Payment(UUID.randomUUID(), fromUserId, toUserId, money, currnecy, LocalDateTime.now(),
                description, PaymentState.ERROR, type, errorMessage));
    }

    public List<Payment> findByUserId(UUID userId) {
        return this.paymentsHistory.findByUserId(userId);
    }

    public Optional<Payment> findByPaymentId(UUID paymentId) {
        return paymentsHistory.findByPaymentId(paymentId);
    }
}
