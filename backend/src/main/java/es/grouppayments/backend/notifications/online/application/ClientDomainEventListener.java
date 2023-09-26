package es.grouppayments.backend.notifications.online.application;

import es.grouppayments.backend._shared.domain.events.GroupEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.grouppayments.backend.notifications.online.domain.OnlineNotificationDispatcher;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class ClientDomainEventListener {
    private final OnlineNotificationDispatcher notificationDispatcher;
    private final GroupMemberService groupMemberService;

    @Order(1)
    @EventListener({OnlineNotificableClientEvent.class})
    public void onNewEvent(OnlineNotificableClientEvent event){
        List<UUID> usersIdToNotify = event.to();

        if(event instanceof GroupEvent groupDomainEvent){
            usersIdToNotify.addAll(this.getGroupMembersUserIdFromGroup(groupDomainEvent));
        }

        usersIdToNotify.forEach(to -> this.notificationDispatcher.send(to, event));
    }

    private List<UUID> getGroupMembersUserIdFromGroup(GroupEvent event){
        return this.groupMemberService.findMembersByGroupId(event.getGroupId()).stream()
                .map(GroupMember::getUserId)
                .toList();
    }
}
