package io.cosmos.order.view.orderview;

import io.cosmos.inventory.event.Shipped;
import io.cosmos.order.aggregate.State;
import io.cosmos.order.event.OrderCanceled;
import io.cosmos.order.event.OrderPlaced;
import io.cosmos.order.view.orderview.store.OrderViewStore;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderViewHandler {
    //
    private final OrderViewStore orderViewStore;

    public OrderViewHandler(OrderViewStore orderViewStore) {
        //
        this.orderViewStore = orderViewStore;
    }

    @EventListener
    public void on(OrderPlaced event) {
        //
        OrderView view = new OrderView(event.getNo(), event.getOrderer().getNo(), event.getItems().get(0).getProduct().getName(), event.getItems().size(), State.Placed);
        this.orderViewStore.create(view);
    }

    @EventListener
    public void on(OrderCanceled event) {
        //
        OrderView view = this.orderViewStore.retrieve(event.getNo());
        view.cancel();
        this.orderViewStore.update(view);
    }

    @EventListener
    public void on(Shipped event) {
        //
        OrderView view = this.orderViewStore.retrieve(event.getOrderNo());
        view.ship();
        this.orderViewStore.update(view);
    }
}
