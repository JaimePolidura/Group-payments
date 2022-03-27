package es.grouppayments.backend.payments.payments;

import _shared.*;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyRepository;
import es.grouppayments.backend.payments.currencies._shared.infrastructure.CurrencyRepositoryInMemory;
import es.grouppayments.backend.users._shared.domain.UserRepository;
import es.grouppayments.backend.users._shared.infrastructure.UserRepositoryInMemory;

public class PaymentsTestMother extends TestMother implements UsingCurrencies, UsingTestPaymentMakerService, UsingCommissionPolicy, UsingUsers {
    private final CurrencyRepository currencyRepository;
    private final TestPaymentMaker testPaymentMaker;
    private final UserRepository userRepository;

    public PaymentsTestMother() {
        this.currencyRepository = new CurrencyRepositoryInMemory();
        this.testPaymentMaker = new FakeTestPaymentMakerService();
        this.userRepository = new UserRepositoryInMemory();
    }

    @Override
    public CurrencyRepository currencyRepository() {
        return this.currencyRepository;
    }

    @Override
    public TestPaymentMaker testPaymentMaker() {
        return this.testPaymentMaker;
    }

    @Override
    public UserRepository usersRepository() {
        return this.userRepository;
    }
}
