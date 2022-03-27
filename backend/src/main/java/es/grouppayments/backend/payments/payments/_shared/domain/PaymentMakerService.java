package es.grouppayments.backend.payments.payments._shared.domain;

import java.util.UUID;

public interface PaymentMakerService {
    String paymentUserToApp(UUID userId, double money, String currencyCode) throws Exception;
    String paymentAppToUser(UUID userId, double money, String currencyCode) throws Exception;
}
