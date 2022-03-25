package es.grouppayments.backend.payments.paymentshistory.getpaymentshistory;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public final class GetPaymentsHistoryQueryHandler implements QueryHandler<GetPaymentsHistoryQuery, GetPaymentsHistoryQueryResponse> {
    private final PaymentHistoryService paymentHistoryService;

    @Override
    public GetPaymentsHistoryQueryResponse handle(GetPaymentsHistoryQuery query) {
        this.ensureCorrectPageNumber(query.getPageNumber());
        this.ensureCorrectItemsPerPage(query.getItemsPerPage());

        int pageNumber = query.getPageNumber();
        int itemsPerPage = query.getItemsPerPage();
        SearchPaymentByType searchPaymentByType = query.getPaymentTypeSearch();

        List<Payment> paymentsFiltered = this.paymentHistoryService.findByUserId(query.getUserId()).stream()
                .filter(searchPaymentByType.paymentTypeMatcherPredicate)
                .sorted() //Payments implemnts comparable
                .skip(getMinIndexFromPageNumberAndItemsPerPage(itemsPerPage, pageNumber))
                .limit(itemsPerPage)
                .toList();

        return new GetPaymentsHistoryQueryResponse(paymentsFiltered);
    }

    private int getMinIndexFromPageNumberAndItemsPerPage(int pageNumber, int itemsPerPage){
        return (pageNumber - 1) * itemsPerPage;
    }

    private void ensureCorrectPageNumber(int numberPage){
        if(numberPage <= 0)
            throw new IllegalQuantity("Page cannot be smaller than 0");
    }

    private void ensureCorrectItemsPerPage(int itemsPerPage){
        if(itemsPerPage > 50 || itemsPerPage <= 10)
            throw new IllegalQuantity("Items per page cannot be bigger than 50 and smaller than 10");
    }
}
