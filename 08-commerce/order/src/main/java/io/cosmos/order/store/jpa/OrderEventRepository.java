package io.cosmos.order.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderEventRepository extends JpaRepository<OrderEventJpo, String> {
    //
    List<OrderEventJpo> findAllByOrderNoAndDeletedOrderBySequenceAsc(String orderNo, boolean deleted);
    List<OrderEventJpo> findAllByOrderNoAndDeletedAndSequenceGreaterThanOrderBySequenceAsc(String orderNo, boolean deleted, long sequence);
    List<OrderEventJpo> findAllByRelayed(boolean relayed);
}
