package io.cosmos.cart.view.itemview.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemViewRepository extends JpaRepository<ItemViewJpo, ItemViewJpo.ItemViewKey> {
    //
    List<ItemViewJpo> findByItemViewKeyCartId(String cartId);
    void deleteByItemViewKeyCartId(String cartId);
}
