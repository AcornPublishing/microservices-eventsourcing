package io.cosmos.notification.stream;

import io.cosmos.notification.command.PutAlert;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationHandler {
    //
    public NotificationHandler() {
        //
        System.out.println("NotificationHandler()");
    }

    @EventListener
    public void on(PutAlert event) {
        //
        System.out.println(event.toString());
    }
}
