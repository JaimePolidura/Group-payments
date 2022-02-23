package es.grouppayments.backend.groupmembers.ongroupdeleted;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnGroupDeleted {
    private final GroupMemberService groupMember;

    @EventListener({GroupDeleted.class})
    public void on(GroupDeleted event){
        groupMember.deleteByGroupId(event.getGroupId());
    }
}
