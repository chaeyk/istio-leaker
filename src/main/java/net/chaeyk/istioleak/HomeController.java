package net.chaeyk.istioleak;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final FirebaseService firebaseService;

    @GetMapping
    public boolean home(@RequestParam(required = false, defaultValue = "token") String token,
                        @RequestParam(required = false, defaultValue = "topic") String topic) throws FirebaseMessagingException {
        return firebaseService.unregister(token, topic);
    }
}
