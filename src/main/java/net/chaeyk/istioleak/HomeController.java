package net.chaeyk.istioleak;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final FirebaseService firebaseService;

    @GetMapping
    public boolean home(@RequestParam(required = false, defaultValue = "f684qUeZTOSV1EJZeUbhHm:APA91bHCmMR19kHia-ZfS0r_TPZR-Kkm2Y2tF7AB4cCLQ5ZLdTK4iOV6e41MDctiOuZW0jPJV3AviztooCpbDJEAis-6a_ANWMRXT7nTjI0HrQ_11haTaMrbI7scFxtyp7nIX83dxhqe") String token,
                        @RequestParam(required = false, defaultValue = "weather") String topic,
                        @RequestParam(required = false, defaultValue = "1000") int count) throws FirebaseMessagingException {
        boolean success = true;
        for (int i = 0; i < count; i++)
            success = firebaseService.unregister(token, topic) && success;
        return success;
    }

    @GetMapping("/register/{topic}/{token}")
    public boolean register(@PathVariable String topic, @PathVariable String token) throws FirebaseMessagingException {
        return firebaseService.register(token, topic);
    }

    @GetMapping("/ver")
    public String ver() {
        return "2";
    }
}
