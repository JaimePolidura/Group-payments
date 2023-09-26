package es.grouppayments.backend._shared.infrastructure.cqrs.query;

import es.grouppayments.backend._shared.infrastructure.cqrs.ApplicationCQRSClassMapper;
import es.jaime.javaddd.domain.cqrs.query.Query;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueryHandlerMapper {
    private final Map<Class<Query>, QueryHandler> queryHandlers;

    public QueryHandlerMapper(ApplicationCQRSClassMapper mapper) {
        queryHandlers = mapper.find(QueryHandler.class);
    }

    public QueryHandler search(Class<? extends Query> queryClass) {
        return queryHandlers.get(queryClass);
    }
}
