package es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public final class PaymentUserInfoRepositoryInMemory implements PaymentUserInfoRepository {
    private final Map<UUID, PaymentUserInfo> stripeUsers;

    public PaymentUserInfoRepositoryInMemory(){
        this.stripeUsers = new HashMap<>();
    }

    @Override
    public void save(PaymentUserInfo stripeUser) {
        this.stripeUsers.put(stripeUser.getUserId(), stripeUser);
    }

    @Override
    public Optional<PaymentUserInfo> findByUserId(UUID userId) {
        return Optional.ofNullable(this.stripeUsers.get(userId));
    }

    @Override
    public void deleteByUserId(UUID userId) {
        this.stripeUsers.remove(userId);
    }
}
