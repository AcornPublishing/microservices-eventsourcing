package io.cosmos.cart.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartEventRepository extends JpaRepository<CartEventJpo, String> {
    //
    List<CartEventJpo> findAllByCartIdAndDeletedOrderBySequenceAsc(String cartId, boolean deleted);
    List<CartEventJpo> findAllByCartIdAndDeletedAndSequenceGreaterThanOrderBySequenceAsc(String cartId, boolean deleted, long sequence);
    List<CartEventJpo> findAllByRelayed(boolean relayed);
    List<CartEventJpo> findAllByCorrelationId(String correlationId);
}
