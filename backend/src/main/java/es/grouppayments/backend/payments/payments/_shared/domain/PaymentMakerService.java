package es.grouppayments.backend.payments.payments._shared.domain;

import java.util.UUID;

public interface PaymentMakerService {
    String makePayment(UUID fromUserId, UUID toUserId, double money, String currencyCode) throws Exception;
}
