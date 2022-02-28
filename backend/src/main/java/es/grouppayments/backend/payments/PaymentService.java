package es.grouppayments.backend.payments;

import java.util.UUID;

public interface PaymentService {
    void makePayment(UUID payerUserId, UUID paidUserId, double moneyPerMember);
}
