package io.cosmos.order.command;

import io.cosmos.eventsourcing.Command;
import io.cosmos.order.aggregate.Item;
import io.cosmos.order.aggregate.Orderer;
import io.cosmos.order.aggregate.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrder extends Command {
    //
    transient private String no;
    private List<Item> items;
    private ShippingAddress shippingAddress;
    transient private Orderer orderer;
}
