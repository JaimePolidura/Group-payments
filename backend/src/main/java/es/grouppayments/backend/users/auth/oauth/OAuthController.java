package es.grouppayments.backend.users.auth.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import es.grouppayments.backend._shared.infrastructure.auth.JWTUtils;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUser;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.StripeUsersService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountRegistered;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeService;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserState;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.event.EventBus;
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
    private final StripeUsersService stripeUsersService;
    private final StripeService stripeService;
    private final JWTUtils jwtUtils;
    private final EventBus eventBus;

    public OAuthController(UsersService usersService, StripeUsersService stripeUsersService, StripeService stripeService,
                           JWTUtils jwtUtils, EventBus eventBus) {
        this.usersService = usersService;
        this.stripeUsersService = stripeUsersService;
        this.stripeService = stripeService;
        this.jwtUtils = jwtUtils;
        this.eventBus = eventBus;
    }

    @PostMapping("/google")
    public ResponseEntity<Response> googleOAuth(@RequestBody Request request) throws IOException {
        var verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singleton(googleClientId));
        var googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), request.token);
        var payload = googleIdToken.getPayload();

        User user = createNewUserIfNotExistsAndGetUserId(request.username, payload.getEmail(), String.valueOf(payload.get("picture")),
                String.valueOf(payload.get("locale")));

        user = this.checkIfRegisteredInStripeConnectedAccount(user);

        String newToken = jwtUtils.generateToken(user.getUserId());

        return ResponseEntity.ok(new Response(newToken, user.getUserId(), user.getState().toString()));
    }

    private User createNewUserIfNotExistsAndGetUserId(String username, String email, String phtoUrl, String country) {
        if(usersService.findByEmail(email).isEmpty())
            return usersService.create(username, email, phtoUrl, country);
        else
            return usersService.findByEmail(email).get();
    }

    private User checkIfRegisteredInStripeConnectedAccount(User user){
        if(user.getState() == UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED){
            StripeUser stripeUser = this.stripeUsersService.getdByUserId(user.getUserId());

            //Hasnt registered in stripe
            if(!stripeUser.isAddedDataInStripeConnectedAccount()){
                boolean hasRegistedConnectedAccount = this.stripeService.hasRegisteredInConnectedAccount(user.getUserId());

                if(hasRegistedConnectedAccount){
                    this.eventBus.publish(new StripeConnectedAccountRegistered(user.getUserId()));

                    return user.updateSignUpState(UserState.SIGNUP_ALL_COMPLETED);
                }
            }
        }

        return user;

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
