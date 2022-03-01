package es.grouppayments.backend.groupmembers.ongroupdeletedtest;

import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.groupmembers.ongroupdeleted.OnGroupDeleted;

public class OnGroupDeletedMother extends GroupMembersTestMother {
    private final OnGroupDeleted eventListenerOnGroupDeleted;

    public OnGroupDeletedMother(){
        this.eventListenerOnGroupDeleted = new OnGroupDeleted(new GroupMemberService(super.groupMemberRepository, testEventBus));
    }

    public void execute(GroupDeleted event){
        this.eventListenerOnGroupDeleted.on(event);
    }
}
