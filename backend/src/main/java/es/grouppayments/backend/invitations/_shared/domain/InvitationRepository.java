package es.grouppayments.backend.invitations._shared.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository {
    void save(Invitation invitiation);

    Optional<Invitation> findByInvitationId(UUID invitationId);

    Optional<Invitation> findByToUserIdAndGroupId(UUID toUserId, UUID groupId);

    List<Invitation> findByToUserId(UUID toUserId);

    void deleteByGroupIdAndToUserId(UUID groupId, UUID toUserId);

    void deleteByGroupId(UUID groupId);
        
    void deleteByInvitationId(UUID invitationId);

    void deleteByUserId(UUID userId);
}
