package es.grouppayments.backend.invitations._shared.infrastructure;

import es.grouppayments.backend.invitations._shared.domain.InvitationRepository;
import es.grouppayments.backend.invitations._shared.domain.Invitation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public final class InvitationRepositoryInMemory implements InvitationRepository {
    private final List<Invitation> invitiations;

    public InvitationRepositoryInMemory(){
        this.invitiations = new ArrayList<>();
    }

    @Override
    public void save(Invitation invitiation) {
        this.invitiations.add(invitiation);
    }

    @Override
    public Optional<Invitation> findByInvitationId(UUID invitationId) {
        return this.invitiations.stream()
                .filter(invitiation -> invitiation.getInvitationId().equals(invitationId))
                .findFirst();
    }

    @Override
    public Optional<Invitation> findByToUserIdAndGroupId(UUID toUserId, UUID groupId) {
        return this.invitiations.stream()
                .filter(invitiation -> invitiation.getGroupId().equals(groupId) && invitiation.getToUserId().equals(toUserId))
                .findFirst();
    }

    @Override
    public List<Invitation> findByToUserId(UUID toUserId) {
        return invitiations.stream()
                .filter(invitiation -> invitiation.getToUserId().equals(toUserId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByGroupIdAndToUserId(UUID groupId, UUID toUserId) {
        this.invitiations.removeIf(invitiation -> invitiation.getGroupId().equals(groupId)
                && invitiation.getToUserId().equals(toUserId));
    }

    @Override
    public void deleteByGroupId(UUID groupId) {
        this.invitiations.removeIf(invitiation -> invitiation.getGroupId().equals(groupId));
    }

    @Override
    public void deleteByInvitationId(UUID invitationId) {
        this.invitiations.removeIf(invitiation -> invitiation.getInvitationId().equals(invitationId));
    }

    @Override
    public void deleteByUserId(UUID userId) {
        this.invitiations.removeIf(invitation -> invitation.getFromUserId().equals(userId)
                || invitation.getToUserId().equals(userId));
    }
}
