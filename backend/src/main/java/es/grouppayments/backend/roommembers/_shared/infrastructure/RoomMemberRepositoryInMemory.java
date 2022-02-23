package es.grouppayments.backend.roommembers._shared.infrastructure;

import es.grouppayments.backend.roommembers._shared.domain.RoomMember;
import es.grouppayments.backend.roommembers._shared.domain.RoomMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RoomMemberRepositoryInMemory implements RoomMemberRepository {
    private final Set<RoomMember> roomMembers;

    public RoomMemberRepositoryInMemory(){
        this.roomMembers = new HashSet<>();
    }

    @Override
    public void save(RoomMember roomMember) {
        this.roomMembers.add(roomMember);
    }

    @Override
    public List<RoomMember> findMembersByRoomId(UUID roomId) {
        return this.roomMembers.stream()
                .filter(room -> room.getRoomId().equals(room))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UUID> findRoomIdByUserId(UUID userId) {
        return this.roomMembers.stream()
                .filter(member -> member.getUserId().equals(userId))
                .map(RoomMember::getRoomId)
                .findFirst();
    }
}
