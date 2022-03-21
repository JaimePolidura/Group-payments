package _shared;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;

public interface UsingPayments {
    PaymentsHistoryRepository paymentRepository();
}
