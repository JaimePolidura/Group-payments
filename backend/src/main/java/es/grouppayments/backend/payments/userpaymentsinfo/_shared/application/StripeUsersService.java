package es.grouppayments.backend.payments.userpaymentsinfo._shared.application;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUser;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUserRepository;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public final class StripeUsersService {
    private final StripeUserRepository stripeUserRepository;

    public void save(StripeUser stripeUser) {
        this.stripeUserRepository.save(stripeUser);
    }

    public StripeUser getdByUserId(UUID userId) {
        return this.stripeUserRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("Not data of payments for that user id"));
    }
}
