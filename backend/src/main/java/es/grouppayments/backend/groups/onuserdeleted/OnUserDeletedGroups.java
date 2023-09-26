package es.grouppayments.backend.groups.onuserdeleted;

import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public final class OnUserDeletedGroups {
    private final GroupService groupService;

    @EventListener({UserDeleted.class})
    public void on(UserDeleted event){
        this.groupService.findByUsernameHost(event.getUserId())
                .ifPresent(group -> this.groupService.deleteById(group.getGroupId()));
    }
}
