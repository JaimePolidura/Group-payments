package es.grouppayments.backend.users.users.getsupportedcurrencies;

import es.grouppayments.backend.users.UsersTestMother;
import es.grouppayments.backend.users.users.getsupportedcurrencies.GetSupportedCurrneciesQuery;
import es.grouppayments.backend.users.users.getsupportedcurrencies.GetSupportedCurrneciesQueryHandler;
import es.grouppayments.backend.users.users.getsupportedcurrencies.GetSupportedCurrneciesQueryResponse;

public class GetSupportedCurrenciesTestMother extends UsersTestMother {
    private final GetSupportedCurrneciesQueryHandler queryHandler;

    public GetSupportedCurrenciesTestMother(){
        this.queryHandler = new GetSupportedCurrneciesQueryHandler();
    }

    public GetSupportedCurrneciesQueryResponse execute(){
        return this.queryHandler.handle(new GetSupportedCurrneciesQuery());
    }
}
