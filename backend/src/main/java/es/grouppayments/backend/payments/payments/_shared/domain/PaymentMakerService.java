package es.grouppayments.backend.payments.payments._shared.domain;

import java.util.UUID;

public interface PaymentMakerService {
    String paymentMemberToApp(UUID userId, double money, String currencyCode) throws Exception;
    String paymentAppToAdmin(UUID userId, double money, String currencyCode) throws Exception;
}
