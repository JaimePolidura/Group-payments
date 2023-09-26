package es.grouppayments.backend.users.users.oauth.infrastructure;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.users._shared.infrastructure.AuthenticationJWTService;
import es.grouppayments.backend.users.users.oauth.domain.AuthenticateUserCommand;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/auth/oauth")
@RequiredArgsConstructor
public class GoogleOAuthController {
    @Value("${google.clientId}")
    private String googleClientId;

    private final UsersService usersService;
    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final AuthenticationJWTService tokenService;

    @PostMapping("/google")
    public ResponseEntity<OAuthResponse> googleOAuth(@RequestBody OAuthRequest request) throws IOException {
        var verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singleton(googleClientId));
        var googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), request.getToken());
        var payload = googleIdToken.getPayload();

        String photoUrl = String.valueOf(payload.get("picture"));
        String countryCode = String.valueOf(payload.get("locale"));
        String username = String.valueOf(payload.get("name"));
        String email = payload.getEmail();

        this.commandBus.dispatch(new AuthenticateUserCommand(username, email, photoUrl, countryCode));
        User userAuthenticated = this.usersService.getByEmail(email);

        String newToken = tokenService.generate(userAuthenticated.getUserId());

        return ResponseEntity.ok(new OAuthResponse(newToken, userAuthenticated));
    }
}
