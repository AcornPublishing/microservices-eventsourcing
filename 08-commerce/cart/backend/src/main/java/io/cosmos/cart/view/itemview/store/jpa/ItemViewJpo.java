package io.cosmos.cart.view.itemview.store.jpa;

import io.cosmos.cart.aggregate.Product;
import io.cosmos.cart.view.itemview.ItemView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_ITEM_VIEW")
@Getter
@Setter
@NoArgsConstructor
public class ItemViewJpo {
    //
    @EmbeddedId
    private ItemViewKey itemViewKey;
    //
    private String productName;
    private int price;
    private int quantity;
    private int total;

    public ItemViewJpo(ItemView view) {
        //
        this.itemViewKey = new ItemViewKey(view.getCartId(), view.getProduct().getNo());
        this.price = view.getProduct().getPrice();
        this.productName = view.getProduct().getName();
        this.quantity = view.getQuantity();
        this.total = this.price * this.quantity;
    }

    public ItemView toView() {
        //
        Product product = new Product(this.itemViewKey.getProductNo(), this.productName, this.price);
        return new ItemView(this.itemViewKey.cartId, product, this.quantity);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class ItemViewKey implements Serializable {
        //
        private String cartId;
        private String productNo;
    }
}
