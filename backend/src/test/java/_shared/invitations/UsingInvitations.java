package _shared.invitations;

import es.grouppayments.backend.invitations._shared.domain.InvitationRepository;
import es.grouppayments.backend.invitations._shared.domain.Invitation;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public interface UsingInvitations {
    InvitationRepository invitationsRepository();

    default void assertInvitationCreated(UUID toUserId, UUID groupId){
        assertTrue(this.invitationsRepository().findByToUserIdAndGroupId(toUserId, groupId).isPresent());
    }

    default void assertInvitationNotCreated(UUID toUserId, UUID groupId){
        assertTrue(this.invitationsRepository().findByToUserIdAndGroupId(toUserId, groupId).isEmpty());
    }


    default void assertInvitationCreated(UUID invitationId) {
        assertTrue(this.invitationsRepository().findByInvitationId(invitationId).isPresent());
    }

    default void assertContentOfInvitation(UUID invitationId, Predicate<Invitation> invnitationContentCondition){
        Invitation invitiationToCheck = this.invitationsRepository().findByInvitationId(invitationId)
                .get();

        assertTrue(invnitationContentCondition.test(invitiationToCheck));
    }


    default void assertInvitationDeleted(UUID invitationId){
        var deleted = this.invitationsRepository().findByInvitationId(invitationId)
                .isEmpty();

        assertTrue(deleted);
    }

    default void addInvitation(UUID invitationId, UUID groupId, UUID fromUserId, UUID toUserId){
        this.invitationsRepository().save(new Invitation(invitationId, groupId, fromUserId, toUserId, LocalDateTime.now(), "hola"));
    }
}
