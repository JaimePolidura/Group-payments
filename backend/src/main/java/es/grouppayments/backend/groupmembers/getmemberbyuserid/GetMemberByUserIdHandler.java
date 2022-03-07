package es.grouppayments.backend.groupmembers.getmemberbyuserid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetMemberByUserIdHandler implements QueryHandler<GetMemberByUserIdQuery, GetMemberByUserIdResponse> {
    private final UsersService usersService;
    private final GroupMemberService groupMemberService;

    @Override
    public GetMemberByUserIdResponse handle(GetMemberByUserIdQuery query) {
        ensureMemberInGroupAndUserInGroup(query.getGroupId(), query.getUserIdToGet(), query.getUserId());

        User user = usersService.findByUserId(query.getUserIdToGet())
                .get();

        return GetMemberByUserIdResponse.fromUserAggregate(user);
    }

    private void ensureMemberInGroupAndUserInGroup(UUID groupId, UUID userIdToGet, UUID userId){
        int totalFind = this.groupMemberService.findMembersByGroupId(groupId).stream()
                .filter(member -> member.getUserId().equals(userIdToGet) || member.getUserId().equals(userId))
                .toList()
                .size();

        //Expect 2 because the user and user to find both should be in the group
        if(totalFind == 2){
            throw new ResourceNotFound("You dont belong to the group Or user not found");
        }
    }
}
