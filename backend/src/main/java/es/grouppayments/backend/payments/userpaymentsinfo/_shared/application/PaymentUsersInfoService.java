package es.grouppayments.backend.payments.userpaymentsinfo._shared.application;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfoRepository;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public final class PaymentUsersInfoService {
    private final PaymentUserInfoRepository stripeUserRepository;

    public void save(PaymentUserInfo stripeUser) {
        this.stripeUserRepository.save(stripeUser);
    }

    public PaymentUserInfo getByUserId(UUID userId) {
        return this.stripeUserRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("Not data of payments for that user id"));
    }

    public void deleteByUserId(UUID userId) {
        this.stripeUserRepository.deleteByUserId(userId);
    }
}
