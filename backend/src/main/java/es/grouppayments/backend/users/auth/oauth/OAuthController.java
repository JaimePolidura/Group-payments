package es.grouppayments.backend.users.auth.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import es.grouppayments.backend._shared.infrastructure.auth.JWTUtils;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UsersService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/auth/oauth")
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

        User user = createNewUserIfNotExistsAndGetUserId(request.username, payload.getEmail(), String.valueOf(payload.get("picture")));
        String newToken = jwtUtils.generateToken(user.getUserId(), user.getState());

        return ResponseEntity.ok(new Response(newToken, user.getUserId(), user.getState().toString()));
    }

    private User createNewUserIfNotExistsAndGetUserId(String username, String email, String phtoUrl) {
        if(usersService.findByEmail(email).isEmpty())
            return usersService.create(username, email, phtoUrl);
        else
            return usersService.findByEmail(email).get();
    }

    @AllArgsConstructor
    private static class Request {
        @Getter private String username;
        @Getter private String token;
    }

    @AllArgsConstructor
    private static class Response{
        @Getter private String token;
        @Getter private UUID userId;
        @Getter private String userState;
    }
}
