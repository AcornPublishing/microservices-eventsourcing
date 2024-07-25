package io.cosmos.order.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Item {
    //
    private String no;
    private Product product;
    private int quantity;

    public Item(String no, Product product, int quantity) {
        //
        this.no = no;
        this.product = product;
        this.quantity = quantity;
    }

    public int getTotal() {
        //
        return this.product.getPrice() * this.quantity;
    }
}
