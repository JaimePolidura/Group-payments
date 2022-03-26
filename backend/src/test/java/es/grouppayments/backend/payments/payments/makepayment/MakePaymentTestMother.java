package es.grouppayments.backend.payments.payments.makepayment;

import _shared.*;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyRepository;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyService;
import es.grouppayments.backend.payments.currencies._shared.infrastructure.CurrencyRepositoryInMemory;
import es.grouppayments.backend.users._shared.domain.UserRepository;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.grouppayments.backend.users._shared.infrastructure.UserRepositoryInMemory;

import java.util.Arrays;
import java.util.UUID;

public class MakePaymentTestMother extends TestMother implements UsingGroups, UsingTestPaymentMakerService, UsingUsers, UsingCurrencies {
    protected static final int FEE = 50;

    private final MakePaymentCommandHandler makePaymentCommandHandler;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final TestPaymentMaker testPaymentMaker;
    private final UserRepository userRepository;
    private final String country;

    public MakePaymentTestMother() {
        this.country = "ES";
        this.userRepository = new UserRepositoryInMemory();
        this.testPaymentMaker = new FakeTestPaymentMakerService();
        this.groupMemberRepository = new GroupMemberRepositoryInMemory();
        this.groupRepository = new GroupsRepositoryInMemory();
        this.makePaymentCommandHandler = new MakePaymentCommandHandler(
                new GroupService(this.groupRepository, super.testEventBus),
                new GroupMemberService(this.groupMemberRepository, super.testEventBus),
                this.testPaymentMaker,
                super.testEventBus,
                FEE,
                new UsersService(this.userRepository),
                new CurrencyService(this.currencyRepository()));
    }

    public void execute(UUID groupId, UUID userId){
        this.makePaymentCommandHandler.handle(new MakePaymentCommand(groupId, userId));
    }

    @Override
    public void addGroup(UUID groupId, UUID adminUserId, double money, UUID... membersUserId) {
        UsingGroups.super.addGroup(groupId, adminUserId, money, membersUserId);

        addUser(adminUserId);
        addUser(membersUserId);
    }

    @Override
    public GroupMemberRepository groupMemberRepository() {
        return this.groupMemberRepository;
    }

    @Override
    public GroupRepository groupRepository() {
        return this.groupRepository;
    }

    @Override
    public TestPaymentMaker testPaymentMaker() {
        return this.testPaymentMaker;
    }

    @Override
    public CurrencyRepository currencyRepository() {
        return new CurrencyRepositoryInMemory();
    }

    @Override
    public UserRepository usersRepository() {
        return this.userRepository;
    }
}
