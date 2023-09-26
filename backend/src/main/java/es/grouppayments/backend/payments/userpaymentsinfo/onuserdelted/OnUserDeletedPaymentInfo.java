package es.grouppayments.backend.payments.userpaymentsinfo.onuserdelted;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnUserDeletedPaymentInfo {
    private final PaymentUsersInfoService stripeUsersService;

    @EventListener({UserDeleted.class})
    public void on(UserDeleted event){
        this.stripeUsersService.deleteByUserId(event.getUserId());
    }
}
