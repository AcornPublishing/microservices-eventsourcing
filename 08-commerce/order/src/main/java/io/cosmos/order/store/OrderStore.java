package io.cosmos.order.store;

import io.cosmos.eventsourcing.AlreadyChangedException;
import io.cosmos.eventsourcing.Event;
import io.cosmos.order.aggregate.Order;
import io.cosmos.order.store.jpa.OrderEventJpo;
import io.cosmos.order.store.jpa.OrderEventRepository;
import io.cosmos.order.store.jpa.OrderJpo;
import io.cosmos.order.store.jpa.OrderRepository;
import io.cosmos.order.store.upcast.OrderEventUpcaster;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderStore {
    //
    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;
    //
    private final OrderEventUpcaster orderEventUpCaster;

    public OrderStore(OrderRepository orderRepository,
                      OrderEventRepository orderEventRepository,
                      OrderEventUpcaster orderEventUpCaster) {
        //
        this.orderRepository = orderRepository;
        this.orderEventRepository = orderEventRepository;
        //
        this.orderEventUpCaster = orderEventUpCaster;
    }

    public Order load(String no) {
        //
        Optional<OrderJpo> orderJpo = this.orderRepository.findById(no);
        if (orderJpo.isEmpty() || orderJpo.get().isDeleted()) {
            throw new NoSuchElementException(String.format("Order(%s) is not found.", no));
        }

        List<OrderEventJpo> eventJpos = new ArrayList<>();

        if (orderJpo.get().getSnapshotTime() > 0) {
            eventJpos = this.orderEventRepository.findAllByOrderNoAndDeletedAndSequenceGreaterThanOrderBySequenceAsc(no, false, orderJpo.get().getSequence());
        } else {
            eventJpos = this.orderEventRepository.findAllByOrderNoAndDeletedOrderBySequenceAsc(no, false);
        }
        List<Event> events = eventJpos.stream().map(OrderEventJpo::toEvent)
                .map(event -> {
                    return this.orderEventUpCaster.upcast(event);
                })
                .collect(Collectors.toList());

        Order order = orderJpo.get().toOrder();
        for (Event event: events) {
            order.apply(event, false);
        }
        order.setVersion(orderJpo.get().getVersion());
        return order;
    }

    public void save(Order order) {
        //
        order.takeSnapshot();

        System.out.println("save(): " + order.snapshot().isPresent());

        List<OrderEventJpo> eventJpos = order.events().stream().map(event -> new OrderEventJpo(order.getNo(), event)).collect(Collectors.toList());
        try {
            OrderJpo jpo = new OrderJpo(order);
            this.orderRepository.save(jpo);
            this.orderEventRepository.saveAll(eventJpos);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new AlreadyChangedException();
        }
    }
}

