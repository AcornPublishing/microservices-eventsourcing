package io.cosmos.order.endpoint;

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
        String userId = UUID.randomUUID().toString().split("-")[0];
        this.httpSession.setAttribute("userId", userId);
        this.httpSession.setAttribute("no", userId);
        this.httpSession.setAttribute("name", "cosmos");
        return userId;
    }
}
