package es.grouppayments.backend.groupmembers.onuserdeletd;


import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public final class OnUserDeletedGroupMember {
    private final GroupMemberService groupMemberService;

    @EventListener({UserDeleted.class})
    public void on(UserDeleted event){
        this.groupMemberService.findGroupMember(event.getUserId())
                .ifPresent(member -> this.groupMemberService.deleteByUserId(member.getUserId()));
    }
}
