package es.grouppayments.backend.groups.onuserdeleted;

import es.grouppayments.backend.groups.GroupTestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;

public class OnUserDeletedGroupsTestMother extends GroupTestMother {
    private final OnUserDeletedGroups eventListener;

    public OnUserDeletedGroupsTestMother(){
        this.eventListener = new OnUserDeletedGroups(
                new GroupService(super.groupRepository(), super.testEventBus)
        );
    }

    public void on(UserDeleted event){
        this.eventListener.on(event);
    }
}
