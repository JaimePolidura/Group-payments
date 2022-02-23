package es.grouppayments.backend._shared.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class Controller {
    public String getLoggedUsername() { return SecurityContextHolder.getContext().getAuthentication().getName(); }

    protected ResponseEntity<String> buildNewHttpResponseOK() {
        return ResponseEntity.ok().body("");
    }

    protected<E> ResponseEntity<E> buildNewHttpResponseOK(E element) {
        return ResponseEntity.ok().body(element);
    }
}
