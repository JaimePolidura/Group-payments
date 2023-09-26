package es.grouppayments.backend.payments.payments;

import _shared.*;
import _shared.comissions.UsingCommissionPolicy;
import _shared.payments.paymentsmaker.FakeTestPaymentMakerService;
import _shared.payments.paymentsmaker.TestPaymentMaker;
import _shared.payments.paymentsmaker.UsingTestPaymentMakerService;
import _shared.users.UsingUsers;
import es.grouppayments.backend.users.users._shared.domain.UserRepository;
import es.grouppayments.backend.users.users._shared.infrastructure.UserRepositoryInMemory;

public class PaymentsTestMother extends TestMother implements UsingTestPaymentMakerService, UsingCommissionPolicy, UsingUsers {
    private final TestPaymentMaker testPaymentMaker;
    private final UserRepository userRepository;

    public PaymentsTestMother() {
        this.testPaymentMaker = new FakeTestPaymentMakerService();
        this.userRepository = new UserRepositoryInMemory();
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
