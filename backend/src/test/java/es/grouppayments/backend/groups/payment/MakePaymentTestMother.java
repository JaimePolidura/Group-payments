package es.grouppayments.backend.groups.payment;

import _shared.*;
import _shared.comissions.UsingCommissionPolicy;
import _shared.groups.UsingGroups;
import _shared.payments.paymentsmaker.FakeTestPaymentMakerService;
import _shared.payments.paymentsmaker.TestPaymentMaker;
import _shared.payments.paymentsmaker.UsingTestPaymentMakerService;
import _shared.threadrunner.FakeThreadRunner;
import _shared.users.UsingUsers;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;
import es.grouppayments.backend.users.users._shared.domain.UserRepository;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.users._shared.infrastructure.UserRepositoryInMemory;

import java.util.UUID;

public class MakePaymentTestMother extends TestMother implements UsingGroups, UsingTestPaymentMakerService, UsingUsers, UsingCommissionPolicy {
    protected static final int FEE = 50;

    private final GroupPaymentCommandHandler makePaymentCommandHandler;
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
        this.makePaymentCommandHandler = new GroupPaymentCommandHandler(
                new FakeThreadRunner(),
                new GroupService(groupRepository, this.testEventBus),
                new GroupMemberService(groupMemberRepository, this.testEventBus),
                this.testPaymentMaker,
                super.testEventBus,
                new UsersService(this.userRepository, super.testEventBus)
        );
    }

    public void execute(UUID groupId, UUID userId){
        this.makePaymentCommandHandler.handle(new GroupPaymentCommand(groupId, userId));
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
    public UserRepository usersRepository() {
        return this.userRepository;
    }
}
