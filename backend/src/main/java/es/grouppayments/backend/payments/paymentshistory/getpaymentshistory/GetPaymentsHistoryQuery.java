package es.grouppayments.backend.payments.paymentshistory.getpaymentshistory;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class GetPaymentsHistoryQuery implements Query {
    @Getter private final UUID userId;
    @Getter private final int pageNumber;
    @Getter private final int itemsPerPage;
    @Getter private final SearchPaymentByType paymentTypeSearch;

    public static GetPaymentsHistoryQuery of(UUID userId, int pageNumber, int itemsPerPage, SearchPaymentByType paymentTypeSearch){
        return new GetPaymentsHistoryQuery(userId, pageNumber, itemsPerPage, paymentTypeSearch);
    }
}
