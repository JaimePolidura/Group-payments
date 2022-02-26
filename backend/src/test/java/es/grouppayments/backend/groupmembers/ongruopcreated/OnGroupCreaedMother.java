package es.grouppayments.backend.groupmembers.ongruopcreated;

import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groupmembers.ongroupcreated.OnGroupCreated;
import es.grouppayments.backend.groups._shared.domain.events.GroupCreated;

public class OnGroupCreaedMother extends TestMother {
    private final OnGroupCreated eventListener;

    public OnGroupCreaedMother (){
        this.eventListener = new OnGroupCreated(
                new GroupMemberService(groupMemberRepository, testEventBus)
        );
    }

    public void triggerEventListener(GroupCreated event){
        eventListener.on(event);
    }
}
