package es.grouppayments.backend.users;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import es.grouppayments.backend._shared.infrastructure.JWTUtils;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UsersService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@RestController
@CrossOrigin
@RequestMapping("/oauth")
public class OAuthController {
    @Value("${google.clientId}")
    private String googleClientId;

    private final UsersService usersService;
    private final JWTUtils jwtUtils;

    public OAuthController(UsersService usersService, JWTUtils jwtUtils) {
        this.usersService = usersService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/google")
    public ResponseEntity<Response> googleOAuth(@RequestBody Request request) throws IOException {
        var netHttpTransport = new NetHttpTransport();
        var jacksonFactory = JacksonFactory.getDefaultInstance();

        var verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory)
                .setAudience(Collections.singleton(googleClientId));

        var googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), request.token);
        var payload = googleIdToken.getPayload();

        usersService.save(request.name, payload.getEmail());
        String newToken = jwtUtils.generateToken(request.name);

        return ResponseEntity.ok(new Response(newToken));
    }

    @AllArgsConstructor
    private static class Request {
        @Getter private String name;
        @Getter private String token;
    }

    @AllArgsConstructor
    private static class Response{
        @Getter private String token;
    }
}
