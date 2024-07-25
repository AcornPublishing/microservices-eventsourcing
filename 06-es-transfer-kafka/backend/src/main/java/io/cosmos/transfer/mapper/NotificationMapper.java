package io.cosmos.transfer.mapper;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.eventsourcing.core.Message;
import io.cosmos.eventsourcing.core.MessageMapper;
import io.cosmos.notification.command.PutMessage;
import io.cosmos.transfer.saga.transfer.event.SagaCompleted;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationMapper implements MessageMapper {
    //
    @Override
    public Optional<Message> map(Event event) {
        if (event.getClass().isAssignableFrom(SagaCompleted.class)) {
            return this.map((SagaCompleted) event);
        }
        return Optional.empty();
    }

    public Optional<Message> map(SagaCompleted event) {
        //
        PutMessage message = new PutMessage("transfer", String.format("Transfer Completed(%s -> %s, %d)",
                event.getFromAccountNo(),
                event.getToAccountNo(),
                event.getAmount()));
        return Optional.of(message);
    }
}
