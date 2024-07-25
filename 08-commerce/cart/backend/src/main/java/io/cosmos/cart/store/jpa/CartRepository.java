package io.cosmos.cart.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartJpo, String> {
    //
}
