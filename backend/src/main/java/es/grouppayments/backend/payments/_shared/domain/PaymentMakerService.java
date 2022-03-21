package es.grouppayments.backend.payments._shared.domain;

import java.util.UUID;

public interface PaymentMakerService {
    String paymentMemberToApp(UUID userId, double money) throws Exception;
    String paymentAppToAdmin(UUID userId, double money) throws Exception;
}
