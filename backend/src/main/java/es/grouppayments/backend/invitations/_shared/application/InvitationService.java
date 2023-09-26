package es.grouppayments.backend.invitations._shared.application;

import es.grouppayments.backend.invitations.create.InvitationCreated;
import es.grouppayments.backend.invitations._shared.domain.InvitationRepository;
import es.grouppayments.backend.invitations._shared.domain.Invitation;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class InvitationService {
    private final InvitationRepository invitationRepository;
    private final EventBus eventBus;

    public void save(UUID invitationId, UUID groupId, UUID toUserId, UUID fromUserId, String description){
        LocalDateTime now = LocalDateTime.now();

        this.invitationRepository.save(new Invitation(invitationId, groupId, fromUserId, toUserId,
                LocalDateTime.now(), description));

        this.eventBus.publish(new InvitationCreated(invitationId, toUserId, fromUserId, groupId, description, now));
    }

    public Invitation getByInvitationOId(UUID invitationId){
        return this.invitationRepository.findByInvitationId(invitationId)
                .orElseThrow(() -> new ResourceNotFound("Invitation not found for that id"));
    }

    public List<Invitation> findByUserIdTo(UUID toUserId){
        return this.invitationRepository.findByToUserId(toUserId);
    }
    
    public void deleteByGroupIdAndToUserId(UUID groupId, UUID toUserId){
        this.invitationRepository.deleteByGroupIdAndToUserId(groupId, toUserId);
    }

    public boolean alreadyInvitedFromGroup(UUID toUserId, UUID groupId){
        return this.invitationRepository.findByToUserIdAndGroupId(toUserId, groupId)
                .isPresent();
    }

    public void deleteByGroupId(UUID groupId) {
        this.invitationRepository.deleteByGroupId(groupId);
    }

    public void deleteByInvitationId(UUID invitationId) {
        this.invitationRepository.deleteByInvitationId(invitationId);
    }

    public void deleteByUserId(UUID userId){
        this.invitationRepository.deleteByUserId(userId);
    }

}
