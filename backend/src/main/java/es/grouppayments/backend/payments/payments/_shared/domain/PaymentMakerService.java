package es.grouppayments.backend.payments.payments._shared.domain;

import es.grouppayments.backend.users.users._shared.domain.Currency;

import java.util.UUID;

public interface PaymentMakerService {
    String makePayment(UUID fromUserId, UUID toUserId, double money, Currency currencyCode) throws Exception;
}
