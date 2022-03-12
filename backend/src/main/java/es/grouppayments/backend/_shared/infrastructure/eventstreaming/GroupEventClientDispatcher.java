package es.grouppayments.backend._shared.infrastructure.eventstreaming;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;

public interface GroupEventClientDispatcher {
    void send(GroupDomainEvent event);
}
