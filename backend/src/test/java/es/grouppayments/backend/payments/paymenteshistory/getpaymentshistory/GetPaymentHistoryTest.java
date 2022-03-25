package es.grouppayments.backend.payments.paymenteshistory.getpaymentshistory;

import com.google.common.base.Optional;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory.getpaymentshistory.GetPaymentsHistoryQuery;
import es.grouppayments.backend.payments.paymentshistory.getpaymentshistory.SearchPaymentByType;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public final class GetPaymentHistoryTest extends GetPaymentHistoryTestMother{
    @Test
    public void shouldGetTypeFilter(){
        final int totalPaymentsOfTheUser = 20;
        UUID userId = UUID.randomUUID();
        addRandoms(userId, 20);

        List<Payment> paymentsFirstPage = execute(GetPaymentsHistoryQuery.of(userId, 1, totalPaymentsOfTheUser, SearchPaymentByType.APP_TO_ADMIN))
                .getPayments();
        assertCollectionNotEmpty(paymentsFirstPage);
    }

    @Test
    public void shouldGetNoTypeFilter(){
        final int totalPaymentsOfTheUser = 20;
        UUID userId = UUID.randomUUID();
        addRandoms(userId, 20);

        List<Payment> paymentsFirstPage = execute(GetPaymentsHistoryQuery.of(userId, 1, 15, SearchPaymentByType.ALL))
                .getPayments();
        assertCollectionSize(paymentsFirstPage, 15);

        List<Payment> paymentsSecondPage = execute(GetPaymentsHistoryQuery.of(userId, 2, 15, SearchPaymentByType.ALL))
                .getPayments();
        assertCollectionSize(paymentsSecondPage, totalPaymentsOfTheUser - paymentsFirstPage.size());

        List<Payment> paymentsThridPage = execute(GetPaymentsHistoryQuery.of(userId, 3, 15, SearchPaymentByType.ALL))
                .getPayments();
        assertEmptyCollection(paymentsThridPage);

        List<Payment> largePager = execute(GetPaymentsHistoryQuery.of(userId, 1, 50, SearchPaymentByType.ALL))
                .getPayments();

        assertCollectionSize(largePager, totalPaymentsOfTheUser);
    }

    @Test
    public void shouldntGetIfThereAreNoPayments(){
        List<Payment> payments = execute(new GetPaymentsHistoryQuery(UUID.randomUUID(), 1, 11, SearchPaymentByType.ALL))
                .getPayments();

        assertEmptyCollection(payments);
    }

    @Test(expected = IllegalQuantity.class)
    public void shouldntGetIllegalPageNumber(){
        execute(new GetPaymentsHistoryQuery(UUID.randomUUID(), 0, 10, SearchPaymentByType.ALL));
    }

    @Test
    public void shouldntGetIllegalItemsPerPage(){
        Assert.assertThrows(IllegalQuantity.class, () -> {
            execute(new GetPaymentsHistoryQuery(UUID.randomUUID(), 1, 0, SearchPaymentByType.ALL));
        });

        Assert.assertThrows(IllegalQuantity.class, () -> {
            execute(new GetPaymentsHistoryQuery(UUID.randomUUID(), 1, -1, SearchPaymentByType.ALL));
        });
    }
}
