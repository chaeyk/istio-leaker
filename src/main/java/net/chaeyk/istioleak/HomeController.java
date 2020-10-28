package net.chaeyk.istioleak;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final FirebaseService firebaseService;

    @GetMapping
    public boolean home(@RequestParam(required = false, defaultValue = "token") String token,
                        @RequestParam(required = false, defaultValue = "topic") String topic,
                        @RequestParam(required = false, defaultValue = "1000") int count) throws FirebaseMessagingException {
        boolean success = true;
        for (int i = 0; i < count; i++)
            success = firebaseService.unregister(token, topic) && success;
        return success;
    }
}
