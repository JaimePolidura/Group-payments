package es.grouppayments.backend.payments.payments;

import _shared.*;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyRepository;
import es.grouppayments.backend.payments.currencies._shared.infrastructure.CurrencyRepositoryInMemory;

public final class PaymentsTestMother extends TestMother implements UsingCurrencies, UsingTestPaymentMakerService {
    private final CurrencyRepository currencyRepository;
    private final TestPaymentMaker testPaymentMaker;

    public PaymentsTestMother() {
        this.currencyRepository = new CurrencyRepositoryInMemory();
        this.testPaymentMaker = new FakeTestPaymentMakerService();
    }

    @Override
    public CurrencyRepository currencyRepository() {
        return this.currencyRepository;
    }

    @Override
    public TestPaymentMaker testPaymentMaker() {
        return this.testPaymentMaker;
    }
}
