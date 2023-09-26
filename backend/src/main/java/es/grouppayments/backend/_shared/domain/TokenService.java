package es.grouppayments.backend._shared.domain;

import java.util.Date;
import java.util.Map;

public interface TokenService {
    String generateToken(String body, Map<String, Object> other, Date expiration);
    String generateToken(String body, Date expiration);
    String getBody(String token);
    String getOther(String token, String key);
    boolean isBodyValid(String token, String expectedBody);
    boolean hasBody(String token);
    boolean isExpired(String token);
}
