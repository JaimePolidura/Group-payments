package es.grouppayments.backend.invitations._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class Invitation extends Aggregate {
    @Getter private final UUID invitationId;
    @Getter private final UUID groupId;
    @Getter private final UUID fromUserId;
    @Getter private final UUID toUserId;
    @Getter private final LocalDateTime date;
    @Getter private final String description;
}
