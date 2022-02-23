package es.grouppayments.backend.groupmembers._shared.infrastructure;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GroupMemberRepositoryInMemory implements GroupMemberRepository {
    private final Set<GroupMember> roomMembers;

    public GroupMemberRepositoryInMemory(){
        this.roomMembers = new HashSet<>();
    }

    @Override
    public void save(GroupMember groupMember) {
        this.roomMembers.add(groupMember);
    }

    @Override
    public List<GroupMember> findMembersByGroupId(UUID groupId) {
        return this.roomMembers.stream()
                .filter(room -> room.getGroupId().equals(room))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UUID> findGroupIdByUserId(UUID userId) {
        return this.roomMembers.stream()
                .filter(member -> member.getUserId().equals(userId))
                .map(GroupMember::getGroupId)
                .findFirst();
    }

    @Override
    public void deleteByUserId(UUID userId) {
        this.roomMembers.removeIf(roomMember -> roomMember.getUserId().equals(userId));
    }

    @Override
    public void deleteByGroupId(UUID groupId) {
        this.roomMembers.removeIf(groupMember -> groupMember.getGroupId().equals(groupId));
    }
}
