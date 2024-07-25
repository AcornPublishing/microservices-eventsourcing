package io.cosmos.notification.command;

import io.cosmos.eventsourcing.annotation.Message;
import io.cosmos.eventsourcing.core.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Message(topic = "notification")
public class PutMessage extends Command {
    //
    private String service;
    private String message;

    @Override
    public String topic() {
        //
        return "notification";
    }
}
