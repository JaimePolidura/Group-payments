package es.grouppayments.backend.payments.payments.makepayment;

import _shared.*;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;

import java.util.UUID;

public class MakePaymentTestMother extends TestMother implements UsingGroups, UsingTestPaymentMakerService {
    protected static final int FEE = 50;

    private final MakePaymentCommandHandler makePaymentCommandHandler;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final TestPaymentMaker testPaymentMaker;

    public MakePaymentTestMother() {
        this.testPaymentMaker = new FakeTestPaymentMakerService();
        this.groupMemberRepository = new GroupMemberRepositoryInMemory();
        this.groupRepository = new GroupsRepositoryInMemory();
        this.makePaymentCommandHandler = new MakePaymentCommandHandler(
                new GroupService(this.groupRepository, super.testEventBus),
                new GroupMemberService(this.groupMemberRepository, super.testEventBus),
                this.testPaymentMaker,
                super.testEventBus,
                FEE
        );
    }

    public void execute(UUID groupId, UUID userId){
        this.makePaymentCommandHandler.handle(new MakePaymentCommand(groupId, userId));
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
}
