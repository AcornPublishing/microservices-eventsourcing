package io.cosmos.order.saga.placeorder;

import io.cosmos.order.aggregate.CancelReason;
import io.cosmos.order.command.CancelOrder;
import io.cosmos.order.event.OrderPlaced;
import io.cosmos.order.saga.event.SagaTimeExpired;
import io.cosmos.order.service.OrderService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrderOrchestrator {
    //
    private static final String SAGA_NAME = "PlaceOrder";
    //
    private final OrderService orderService;
    //
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TaskScheduler taskScheduler;

    public PlaceOrderOrchestrator(OrderService orderService,
                                  ApplicationEventPublisher applicationEventPublisher,
                                  TaskScheduler taskScheduler) {
        //
        this.orderService = orderService;
        //
        this.applicationEventPublisher = applicationEventPublisher;
        this.taskScheduler = taskScheduler;
    }

    @EventListener
    public void on(OrderPlaced event) {
        //
        SagaTimeout sagaTimeout = new SagaTimeout(event.getNo(), SAGA_NAME, this.applicationEventPublisher);
        this.taskScheduler.schedule(sagaTimeout, SagaTimeout.expireTime(10));
    }

    @EventListener
    public void on(SagaTimeExpired event) {
        //
        if (SAGA_NAME.equals(event.getSagaType())) {
            System.out.println("PlaceOrderChoreographer.on(SagaTimeExpired);");
            CancelOrder command = new CancelOrder(event.getCorrelationId(), CancelReason.Timeout);
            this.orderService.cancel(command);
        }
    }

}