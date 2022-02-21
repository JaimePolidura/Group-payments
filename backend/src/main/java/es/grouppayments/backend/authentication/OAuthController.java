package es.grouppayments.backend.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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

    @PostMapping("/google")
    public ResponseEntity<?> googleOAuth(@RequestBody Request request) throws IOException {
        var netHttpTransport = new NetHttpTransport();
        var jacksonFactory = JacksonFactory.getDefaultInstance();

        var verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory)
                .setAudience(Collections.singleton(googleClientId));

        var googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), request.token);
        var payload = googleIdToken.getPayload();

        return ResponseEntity.ok(payload);
    }

    @AllArgsConstructor
    private static class Request {
        @Getter private String token;
    }
}
