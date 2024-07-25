package io.cosmos.cart.service;

import io.cosmos.cart.aggregate.Cart;
import io.cosmos.cart.command.*;
import io.cosmos.cart.query.QueryCart;
import io.cosmos.cart.query.QueryCartView;
import io.cosmos.cart.store.CartStore;
import io.cosmos.cart.view.itemview.ItemView;
import io.cosmos.cart.view.itemview.store.ItemViewStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartService {
    //
    private final CartStore cartStore;
    private final ItemViewStore itemViewStore;

    public CartService(CartStore cartStore,
                       ItemViewStore itemViewStore) {
        //
        this.cartStore = cartStore;
        this.itemViewStore = itemViewStore;
    }

    public void createCart(CreateCart command) {
        //
        Cart cart = new Cart(command);
        this.cartStore.save(cart);
    }

    public Cart queryCart(QueryCart query) {
        //
        return this.cartStore.load(query.getCartId());
    }

    public void addItem(AddItem command) {
        //
        Cart cart = this.cartStore.load(command.getCartId());
        cart.addItem(command);
        this.cartStore.save(cart);
    }

    public void changeQuantity(ChangeQuantity command) {
        //
        Cart cart = this.cartStore.load(command.getCartId());
        cart.changeQuantity(command);
        this.cartStore.save(cart);
    }

    public void removeItem(RemoveItem command) {
        //
        Cart cart = this.cartStore.load(command.getCartId());
        cart.removeItem(command);
        this.cartStore.save(cart);
    }

    public List<ItemView> queryCartView(QueryCartView query) {
        //
        return this.itemViewStore.retrieve(query.getCartId());
    }

    public void removeCart(RemoveCart command) {
        //
        Cart cart = this.cartStore.load(command.getCartId());
        cart.remove(command);
        this.cartStore.save(cart);
    }
}
