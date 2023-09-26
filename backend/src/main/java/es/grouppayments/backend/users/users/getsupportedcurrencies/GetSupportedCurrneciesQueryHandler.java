package es.grouppayments.backend.users.users.getsupportedcurrencies;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public final class GetSupportedCurrneciesQueryHandler implements QueryHandler<GetSupportedCurrneciesQuery, GetSupportedCurrneciesQueryResponse> {
    @Override
    public GetSupportedCurrneciesQueryResponse handle(GetSupportedCurrneciesQuery getSupportedCurrneciesQuery) {
        return GetSupportedCurrneciesQueryResponse.fromCurrenciesArray(Currency.values());
    }
}
