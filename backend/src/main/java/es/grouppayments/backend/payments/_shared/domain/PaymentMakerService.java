package es.grouppayments.backend.payments._shared.domain;

import java.util.UUID;

public interface PaymentMakerService {
    void makePayment(UUID payerUserId, UUID paidUserId, double money);
    boolean enoughBalance(UUID payerUserId, double money);
}
