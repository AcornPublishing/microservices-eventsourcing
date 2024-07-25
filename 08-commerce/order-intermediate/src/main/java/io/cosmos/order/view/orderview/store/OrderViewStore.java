package io.cosmos.order.view.orderview.store;

import io.cosmos.order.view.orderview.OrderView;
import io.cosmos.order.view.orderview.store.jpo.OrderViewJpo;
import io.cosmos.order.view.orderview.store.jpo.OrderViewRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderViewStore {
    //
    private final OrderViewRepository orderViewRepository;

    public OrderViewStore(OrderViewRepository orderViewRepository) {
        //
        this.orderViewRepository = orderViewRepository;
    }

    public void create(OrderView view) {
        //
        this.orderViewRepository.save(new OrderViewJpo(view));
    }

    public List<OrderView> retrieveByUser(String userNo) {
        //
        List<OrderViewJpo> viewJpos = this.orderViewRepository.findAllByUserNo(userNo);
        return viewJpos.stream().map(OrderViewJpo::toView).collect(Collectors.toList());
    }

    public OrderView retrieve(String orderNo) {
        //
        Optional<OrderViewJpo> orderViewJpo = this.orderViewRepository.findById(orderNo);
        if (orderViewJpo.isEmpty()) {
            throw new NoSuchElementException(String.format("OrderView(%s) is not found.", orderNo));
        }
        return orderViewJpo.get().toView();
    }

    public void update(OrderView view) {
        //
        this.orderViewRepository.save(new OrderViewJpo(view));
    }
}
