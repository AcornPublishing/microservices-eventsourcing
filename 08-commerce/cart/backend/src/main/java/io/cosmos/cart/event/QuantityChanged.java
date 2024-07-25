package io.cosmos.cart.event;

import io.cosmos.eventsourcing.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityChanged extends Event {
    //
    private String cartId;
    private String productNo;
    private int quantity;
}
