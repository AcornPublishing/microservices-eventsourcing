package io.cosmos.order.event;

import io.cosmos.eventsourcing.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCanceled extends Event {
    //
    private String no;
}
