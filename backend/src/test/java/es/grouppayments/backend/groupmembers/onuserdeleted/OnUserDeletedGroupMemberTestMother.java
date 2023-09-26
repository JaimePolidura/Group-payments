package es.grouppayments.backend.groupmembers.onuserdeleted;

import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groupmembers.onuserdeletd.OnUserDeletedGroupMember;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;

public class OnUserDeletedGroupMemberTestMother extends GroupMembersTestMother {
    private final OnUserDeletedGroupMember eventListener;

    public OnUserDeletedGroupMemberTestMother(){
        this.eventListener = new OnUserDeletedGroupMember(
                new GroupMemberService(super.groupMemberRepository(), super.testEventBus)
        );
    }

    public void on(UserDeleted event){
        this.eventListener.on(event);
    }
}
