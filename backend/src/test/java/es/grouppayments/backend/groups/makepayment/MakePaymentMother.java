package es.grouppayments.backend.groups.makepayment;

import com.sun.security.auth.UnixNumericUserPrincipal;
import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments.FakePaymentService;

import java.util.UUID;

public class MakePaymentMother extends TestMother {
    private final MakePaymentCommandHandler makePaymentCommandHandler;
    private final FakePaymentService fakePaymentService;

    public MakePaymentMother() {
        this.fakePaymentService = new FakePaymentService();

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
