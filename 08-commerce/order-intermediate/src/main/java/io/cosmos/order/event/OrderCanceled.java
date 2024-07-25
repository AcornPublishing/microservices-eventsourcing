package io.cosmos.order.event;

import io.cosmos.eventsourcing.Event;
import io.cosmos.order.aggregate.CancelReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCanceled extends Event {
    //
    private static final String REVISION = "2";
    private String no;
    private CancelReason reason;

    private OrderCanceled() {
        //
        this.revision(REVISION);
    }

}
