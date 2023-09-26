package es.grouppayments.backend.notifications.offline._shared.infrastructure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseMessagingProvider {
    @Bean
    @SneakyThrows
    public FirebaseMessaging firebaseMessaging(){
        var credentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());

        var options = FirebaseOptions
                .builder()
                .setCredentials(credentials)
                .build();

        return FirebaseMessaging.getInstance(FirebaseApp.initializeApp(options));
    }
}
