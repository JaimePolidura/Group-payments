package es.grouppayments.backend.payments;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FakePaymentService implements PaymentService{
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
