package es.grouppayments.backend.groups.onpaymentdone;

import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments.payments._shared.domain.events.GroupPaymentDone;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnPaymentDoneDeleteGroup {
    private final GroupService groupService;

    @EventListener({GroupPaymentDone.class})
    @Order(100)
    public void on(GroupPaymentDone paymentDone){
        this.groupService.deleteById(paymentDone.getGroupId());
    }
}
