package es.grouppayments.backend.groups.makepayment;

import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments._shared.infrastructure.FakePaymentMakerService;

import java.util.UUID;

public class MakePaymentMother extends TestMother {
    private final MakePaymentCommandHandler makePaymentCommandHandler;
    private final FakePaymentMakerService fakePaymentService;

    public MakePaymentMother() {
        this.fakePaymentService = new FakePaymentMakerService();

        this.makePaymentCommandHandler = new MakePaymentCommandHandler(
                new GroupService(groupRepository, testEventBus),
                new GroupMemberService(groupMemberRepository, testEventBus),
                fakePaymentService,
                super.testEventBus
        );
    }

    public void makePayment(UUID gruopId){
        this.makePaymentCommandHandler.handle(new MakePaymentCommand(
                gruopId
        ));
    }

    public void willFail(){
        this.fakePaymentService.willFail();
    }
}
