package es.grouppayments.backend.payments.userpaymentsinfo._shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface StripeUserRepository {
    void save(StripeUser stripeUser);

    Optional<StripeUser> findByUserId(UUID userId);
}
