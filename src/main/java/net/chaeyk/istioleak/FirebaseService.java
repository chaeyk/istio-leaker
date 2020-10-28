package net.chaeyk.istioleak;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.ImmutableList;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseService implements InitializingBean {

    private final ResourceLoader resourceLoader;

    private FirebaseApp firebaseApp;

    @Value("${firebase.keyfile}")
    private String keyfile;

    @Override
    public void afterPropertiesSet() throws Exception {

        String fcmDatabaseUrl = "https://istioleaker.firebaseio.com/";
        String appName = "istioLeaker";


        InputStream keyStream = new FileInputStream(keyfile);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(keyStream))
                .setDatabaseUrl(fcmDatabaseUrl)
                .build();
        firebaseApp = FirebaseApp.initializeApp(options, appName);
        FirebaseDatabase.getInstance(firebaseApp).setPersistenceEnabled(true);
    }

    public boolean register(String token, String topic) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance(firebaseApp)
                .subscribeToTopic(ImmutableList.of(token), topic);
        response.getErrors().forEach(error -> log.error("register failed: {}", error.getReason()));
        return response.getErrors().isEmpty();
    }

    public boolean unregister(String token, String topic) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance(firebaseApp)
                .unsubscribeFromTopic(ImmutableList.of(token), topic);
        response.getErrors().forEach(error -> log.error("unregister failed: {}", error.getReason()));
        return response.getErrors().isEmpty();
    }
}
