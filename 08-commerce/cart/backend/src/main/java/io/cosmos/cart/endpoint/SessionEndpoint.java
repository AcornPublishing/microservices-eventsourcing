package io.cosmos.cart.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class SessionEndpoint {
    //
    private final HttpSession httpSession;

    public SessionEndpoint(HttpSession httpSession) {
        //
        this.httpSession = httpSession;
    }

    @PostMapping(value = "/session")
    public String createSession() {
        //
        String userId = "id9xf8w1";
        this.httpSession.setAttribute("userId", userId);
        return userId;
    }
}
