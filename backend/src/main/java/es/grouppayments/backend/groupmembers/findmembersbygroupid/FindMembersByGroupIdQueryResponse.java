package es.grouppayments.backend.groupmembers.findmembersbygroupid;

import es.grouppayments.backend.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.Data;

import java.util.List;

@Data
public class FindMembersByGroupIdQueryResponse implements QueryResponse {
    private final List<User> users;
}
