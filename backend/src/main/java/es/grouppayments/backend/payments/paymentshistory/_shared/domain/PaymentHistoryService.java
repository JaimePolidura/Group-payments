package es.grouppayments.backend.payments.paymentshistory._shared.domain;

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

    public void save(UUID userId, double money, String groupDescription, PaymentType type){
        this.paymentsHistory.save(new Payment(
                UUID.randomUUID(), LocalDateTime.now(), type == PaymentType.APP_TO_USER ? "APP" : userId.toString(),
                type == PaymentType.USER_TO_APP ? "APP" : userId.toString(), money, groupDescription, PaymentState.SUCCESS, type, null
        ));
    }

    public void save(UUID userId, double money, String groupDescription, PaymentType type, String errorMessage){
        this.paymentsHistory.save(new Payment(
                UUID.randomUUID(), LocalDateTime.now(), type == PaymentType.APP_TO_USER ? "APP" : userId.toString(),
                type == PaymentType.USER_TO_APP ? "APP" : userId.toString(), money, groupDescription, PaymentState.ERROR, type, errorMessage
        ));
    }

    public List<Payment> findByUserId(UUID userId) {
        return this.paymentsHistory.findByUserId(userId);
    }

    public Optional<Payment> findByPaymentId(UUID paymentId) {
        return paymentsHistory.findByPaymentId(paymentId);
    }
}
