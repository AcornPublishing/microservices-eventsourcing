package io.cosmos.order.event;

import io.cosmos.eventsourcing.Event;
import io.cosmos.order.aggregate.CancelReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCanceledWithReason extends Event {
    //
    private String no;
    private CancelReason reason;
}
