package io.cosmos.cart.view.itemview;

import io.cosmos.cart.aggregate.Product;
import io.cosmos.cart.event.CartRemoved;
import io.cosmos.cart.event.ItemAdded;
import io.cosmos.cart.event.ItemRemoved;
import io.cosmos.cart.event.QuantityChanged;
import io.cosmos.cart.view.itemview.store.ItemViewStore;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ItemViewHandler {
    //
    private final ItemViewStore itemViewStore;

    public ItemViewHandler(ItemViewStore itemViewStore) {
        //
        this.itemViewStore = itemViewStore;
    }

    @Async
    @EventListener
    public void on(ItemAdded event) {
        //
        ItemView itemView = new ItemView(event.getCartId(), new Product(event.getProductNo(), event.getProductName(), event.getPrice()), event.getQuantity());
        this.itemViewStore.create(itemView);
    }

    @Async
    @EventListener
    public void on(QuantityChanged event) {
        //
        ItemView itemView = this.itemViewStore.retrieve(event.getCartId(), event.getProductNo());
        itemView.changeQuantity(event.getQuantity());
        this.itemViewStore.update(itemView);
    }

    @Async
    @EventListener
    public void on(ItemRemoved event) {
        //
        this.itemViewStore.delete(event.getCartId(), event.getProductNo());
    }

    @Async
    @EventListener
    public void on(CartRemoved event) {
        //
        this.itemViewStore.delete(event.getCartId());
    }
}
