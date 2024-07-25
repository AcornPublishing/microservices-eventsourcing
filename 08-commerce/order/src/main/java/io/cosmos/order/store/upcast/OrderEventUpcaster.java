package io.cosmos.order.store.upcast;

import io.cosmos.eventsourcing.Event;
import io.cosmos.eventsourcing.Upcaster;
import io.cosmos.order.aggregate.CancelReason;
import io.cosmos.order.event.OrderCanceled;
import io.cosmos.order.event.OrderCanceledWithReason;
import org.springframework.stereotype.Component;

@Component
public class OrderEventUpcaster implements Upcaster {
    //
    public Event upcast(Event event) {
        if (event instanceof OrderCanceled) {
            return this.upcast(event.toEvent(OrderCanceled.class));
        }
        return event;
    }

    private OrderCanceledWithReason upcast(OrderCanceled event) {
        //
        return new OrderCanceledWithReason(event.getNo(), CancelReason.Etc);
    }
}
