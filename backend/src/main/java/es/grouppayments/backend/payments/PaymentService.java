package es.grouppayments.backend.payments;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    void makePayment(List<UUID> membersUsersId, double moneyPerMember);
}
