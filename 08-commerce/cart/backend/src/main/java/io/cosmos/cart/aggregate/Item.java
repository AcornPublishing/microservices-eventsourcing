package io.cosmos.cart.aggregate;

import io.cosmos.cart.exception.MinimumQuantityException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    //
    private Product product;
    private int quantity;

    public void increase() {
        //
        this.quantity++;
    }

    private void decrease() {
        //
        if (this.quantity == 1) {
            throw new MinimumQuantityException();
        }
        this.quantity--;
    }

    public void changeQuantity(int quantity) {
        //
        this.quantity = quantity;
    }
}
