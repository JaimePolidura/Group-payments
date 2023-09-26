package es.grouppayments.backend.users.usersimage.get;

import es.grouppayments.backend.users.usersimage._shared.application.UsersImagesService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class GetUserImageQueryHandler implements QueryHandler<GetUserImageQuery, GetUserImageQueryResponse> {
    private final UsersImagesService usersImagesService;

    @Override
    public GetUserImageQueryResponse handle(GetUserImageQuery query) {
        return new GetUserImageQueryResponse(this.usersImagesService.getById(query.getImageId()).getContent());
    }
}
