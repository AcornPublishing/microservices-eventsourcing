package io.cosmos.order.service;

import io.cosmos.order.aggregate.Order;
import io.cosmos.order.command.CancelOrder;
import io.cosmos.order.command.CompleteOrder;
import io.cosmos.order.command.PlaceOrder;
import io.cosmos.order.command.Ship;
import io.cosmos.order.query.QueryOrder;
import io.cosmos.order.query.QueryOrders;
import io.cosmos.order.store.OrderStore;
import io.cosmos.order.view.orderview.OrderView;
import io.cosmos.order.view.orderview.store.OrderViewStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    //
    private final OrderStore orderStore;
    private final OrderViewStore orderViewStore;

    public OrderService(OrderStore orderStore,
                        OrderViewStore orderViewStore) {
        //
        this.orderStore = orderStore;
        this.orderViewStore = orderViewStore;
    }

    public String place(PlaceOrder command) {
        //
        Order order = new Order(command);
        this.orderStore.save(order);
        return command.getNo();
    }

    public Order queryOrder(QueryOrder query) {
        //
        return this.orderStore.load(query.getNo());
    }

    public List<OrderView> queryOrders(QueryOrders query) {
        //
        return this.orderViewStore.retrieveByUser(query.getUserNo());
    }

    public void cancel(CancelOrder command) {
        //
        Order order = this.orderStore.load(command.getNo());
        order.cancel(command);
        this.orderStore.save(order);
    }

    public void ship(Ship command) {
        //
        Order order = this.orderStore.load(command.getNo());
        order.ship(command);
        this.orderStore.save(order);
    }

    public void complete(CompleteOrder command) {
        Order order = this.orderStore.load(command.getNo());
        order.complete(command);
        this.orderStore.save(order);
    }
}
