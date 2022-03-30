package es.grouppayments.backend._shared.application.notifications;

import es.grouppayments.backend._shared.domain.events.GroupDomainEvent;
import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.grouppayments.backend._shared.domain.notifications.NotificationClientDispatcher;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class ClientDomainEventListener {
    private final NotificationClientDispatcher notificationDispatcher;
    private final GroupMemberService groupMemberService;

    @SneakyThrows
    @Order(1)
    @EventListener({NotificableClientDomainEvent.class})
    public void onNewEvent(NotificableClientDomainEvent event){
        System.out.println("hola");
        System.out.println(event.name());
        System.out.println(event.to());

        List<UUID> usersIdToNotify = event.to();

        if(event instanceof GroupDomainEvent groupDomainEvent){
            usersIdToNotify = this.getGroupMembersUserIdFromGroup(groupDomainEvent);
        }

        usersIdToNotify.forEach(to -> this.notificationDispatcher.send(to, event));
    }

    private List<UUID> getGroupMembersUserIdFromGroup(GroupDomainEvent event){
        return this.groupMemberService.findMembersByGroupId(event.getGroupId()).stream()
                .map(GroupMember::getUserId)
                .toList();
    }
}
