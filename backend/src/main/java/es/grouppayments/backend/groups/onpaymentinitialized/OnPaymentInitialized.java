package es.grouppayments.backend.groups.onpaymentinitialized;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.grouppayments.backend.payments.stripe.makepayment.PaymentInitialized;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnPaymentInitialized {
    private final GroupService groupService;

    @EventListener({PaymentInitialized.class})
    public void on(PaymentInitialized event){
        Group group = groupService.findByIdOrThrowException(event.getGroupId());

        this.groupService.changeState(group.getGroupId(), GroupState.PAYING);
    }
}
