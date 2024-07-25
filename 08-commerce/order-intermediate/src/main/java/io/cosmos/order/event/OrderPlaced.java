package io.cosmos.order.event;

import io.cosmos.eventsourcing.Event;
import io.cosmos.order.aggregate.Item;
import io.cosmos.order.aggregate.Orderer;
import io.cosmos.order.aggregate.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlaced extends Event {
    //
    private String no;
    private List<Item> items;
    private ShippingAddress shippingAddress;
    private Orderer orderer;
}
