package es.grouppayments.backend.payments.userpaymentsinfo._shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface PaymentUserInfoRepository {
    void save(PaymentUserInfo stripeUser);

    Optional<PaymentUserInfo> findByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
