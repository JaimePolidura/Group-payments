package es.grouppayments.backend.roommembers._shared.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomMemberRepository {
    void save(RoomMember roomMember);

    List<RoomMember> findMembersByRoomId(UUID roomId);

    Optional<UUID> findRoomIdByUserId(UUID userId);
}
