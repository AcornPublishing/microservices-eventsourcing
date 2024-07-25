package io.cosmos.order.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderJpo, String> {
    //
}
