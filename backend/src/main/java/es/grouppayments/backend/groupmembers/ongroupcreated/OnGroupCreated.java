package es.grouppayments.backend.groupmembers.ongroupcreated;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups.create.GroupCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OnGroupCreated {
    private final GroupMemberService groupMembers;

    @EventListener({GroupCreated.class})
    public void on(GroupCreated event){
        this.groupMembers.save(new GroupMember(
                event.getAdminUserId(),
                event.getGroupId(),
                GroupMemberRole.ADMIN
        ));
    }
}
