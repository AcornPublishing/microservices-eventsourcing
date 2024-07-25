package io.cosmos.order.stream;

import io.cosmos.inventory.event.Shipped;
import io.cosmos.order.command.Ship;
import io.cosmos.order.service.OrderService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListener {
    //
    private final OrderService orderService;

    public InventoryListener(OrderService orderService) {
        //
        this.orderService = orderService;
    }

    @EventListener
    public void on(Shipped event) {
        //
        Ship command = new Ship(event.getOrderNo());
        this.orderService.ship(command);
    }
}
