package es.grouppayments.backend.payments._shared.domain;

import java.util.UUID;

public interface PaymentMakerService {
    void paymentMemberToApp(UUID userId, double money) throws Exception;
    void paymentAppToAdmin(UUID userId, double money) throws RuntimeException;
}
