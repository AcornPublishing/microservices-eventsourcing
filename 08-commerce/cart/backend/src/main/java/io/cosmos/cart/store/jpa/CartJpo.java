package io.cosmos.cart.store.jpa;

import io.cosmos.cart.aggregate.Cart;
import io.cosmos.eventsourcing.Snapshot;
import io.cosmos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TB_CART")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartJpo {
    //
    @Id
    private String cartId;
    //
    private boolean deleted;
    //
    private long sequence;
    @Version
    private long version;
    //
    @Lob
    @Column(name = "SNAPSHOT_PAYLOAD")
    private String snapshotPayload;
    @Column(name = "SNAPSHOT_TIME")
    private long snapshotTime;
    @Column(name = "SNAPSHOT_SEQ")
    private long snapshotSequence;

    public CartJpo(Cart cart) {
        //
        this.cartId = cart.getCartId();
        //
        this.deleted = cart.deleted();
        //
        this.sequence = cart.sequence();
        this.version = cart.getVersion();
        //
        if (cart.snapshot().isPresent()) {
            this.snapshotPayload = cart.snapshot().get().getPayload();
            this.snapshotTime = cart.snapshot().get().getTime();
            this.snapshotSequence = cart.snapshot().get().getSequence();
        }
    }

    public Cart toCart() {
        //
        Cart cart = null;

        if (this.snapshotTime > 0) {
            cart = JsonUtil.fromJson(this.snapshotPayload, Cart.class);
            cart.snapshot(new Snapshot(this.snapshotPayload, this.snapshotTime, this.snapshotSequence));
            cart.sequence(this.sequence);
            return cart;
        }
        cart = JsonUtil.fromJson("{}", Cart.class);
        return cart;
    }

}
