package es.grouppayments.backend.roommembers._shared.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomMemberService {
    private final RoomMemberRepository roomMembers;

    public void save(RoomMember roomMember){
        this.roomMembers.save(roomMember);
    }

    public List<RoomMember> findMembersByRoomId(UUID roomId){
        return this.roomMembers.findMembersByRoomId(roomId);
    }

    public Optional<UUID> findRoomIdByUserId(UUID userId){
        return this.roomMembers.findRoomIdByUserId(userId);
    }

}
