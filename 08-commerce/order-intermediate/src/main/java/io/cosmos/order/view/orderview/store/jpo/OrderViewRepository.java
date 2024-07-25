package io.cosmos.order.view.orderview.store.jpo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderViewRepository extends JpaRepository<OrderViewJpo, String> {
    //
    List<OrderViewJpo> findAllByUserNo(String userNo);
}
