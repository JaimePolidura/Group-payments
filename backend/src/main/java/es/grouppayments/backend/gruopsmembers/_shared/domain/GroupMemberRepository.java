package es.grouppayments.backend.gruopsmembers._shared.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMemberRepository {
    void save(GroupMember groupMember);

    List<GroupMember> findMembersByGroupId(UUID groupId);

    Optional<UUID> findGroupIdByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
