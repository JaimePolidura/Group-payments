package es.grouppayments.backend.payments._shared.infrastructure;

import es.grouppayments.backend.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments._shared.domain.UnprocessablePayment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FakePaymentMakerService implements PaymentMakerService {
    private boolean fail;

    public void willFail(){
        this.fail = true;
    }

    public void wontFail(){
        this.fail = false;
    }

    @Override
    public void makePayment(UUID payerUserId, UUID paidUserId, double moneyPerMember) {
        if(fail){
            throw new UnprocessablePayment("Error");
        }
    }
}
