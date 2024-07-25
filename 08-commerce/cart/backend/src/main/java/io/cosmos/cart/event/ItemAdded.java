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
public class ItemAdded extends Event {
    //
    private String cartId;
    //
    private String productNo;
    private String productName;
    private int price;
    private int quantity;
}
