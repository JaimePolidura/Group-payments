package es.grouppayments.backend.users.auth.oauth._shared.infrastructure;

import es.grouppayments.backend.users._shared.domain.UserState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Service
public final class ProtectedRoutesUserState {
    private final Map<String, Predicate<UserState>> routes;
    
    public ProtectedRoutesUserState() {
        this.routes = new HashMap<>();
    }

    public boolean isValid(String route, UserState userState){
        return this.routes.get(route) != null && this.routes.get(route).test(userState);
    }
}
