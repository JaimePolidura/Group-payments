package es.grouppayments.backend.payments.stripe._shared.infrastructure;

import es.grouppayments.backend.payments.stripe._shared.domain.StripeUser;
import es.grouppayments.backend.payments.stripe._shared.domain.StripeUserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public final class StripeUserRepositoryInMemory implements StripeUserRepository {
    private final Map<UUID, StripeUser> stripeUsers;

    public StripeUserRepositoryInMemory(){
        this.stripeUsers = new HashMap<>();
    }

    @Override
    public void save(StripeUser stripeUser) {
        this.stripeUsers.put(stripeUser.getUserId(), stripeUser);
    }

    @Override
    public Optional<StripeUser> findByUserId(UUID userId) {
        return Optional.ofNullable(this.stripeUsers.get(userId));
    }
}
