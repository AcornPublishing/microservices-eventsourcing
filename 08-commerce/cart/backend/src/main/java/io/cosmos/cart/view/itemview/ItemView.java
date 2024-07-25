package io.cosmos.cart.view.itemview;

import io.cosmos.cart.aggregate.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemView {
    //
    private String cartId;
    //
    private Product product;
    private int quantity;
    //
    private int total;

    public ItemView(String cartId, Product product, int quantity) {
        //
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
        //
        this.total = product.getPrice() * quantity;
    }

    public void changeQuantity(int quantity) {
        //
        this.quantity = quantity;
        this.total = this.product.getPrice() * quantity;
    }
}
