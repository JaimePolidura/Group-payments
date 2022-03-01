package es.grouppayments.backend.groupmembers.ongruopcreated;

import _shared.TestMother;
import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groupmembers.ongroupcreated.OnGroupCreated;
import es.grouppayments.backend.groups._shared.domain.events.GroupCreated;

public class OnGroupCreatedMother extends GroupMembersTestMother {
    private final OnGroupCreated eventListener;

    public OnGroupCreatedMother(){
        this.eventListener = new OnGroupCreated(
                new GroupMemberService(groupMemberRepository, testEventBus)
        );
    }

    public void triggerEventListener(GroupCreated event){
        eventListener.on(event);
    }
}
