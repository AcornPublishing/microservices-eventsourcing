package io.cosmos.cart.store.jpa;

import io.cosmos.cart.aggregate.Cart;
import io.cosmos.eventsourcing.Event;
import io.cosmos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CART_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartEventJpo {
    //
    @Id
    private String eventId;
    //
    private String cartId;
    //
    @Lob
    private String payload;
    private String eventType;
    //
    private String correlationId;
    //
    private long sequence;
    //
    private boolean relayed;
    private boolean propagate;
    private boolean deleted;

    public CartEventJpo(Cart cart, Event event) {
        //
        this.eventId = event.eventId();
        this.cartId = cart.getCartId();
        //
        this.payload = event.payload();
        this.eventType = event.typeName();
        //
        this.correlationId = event.correlationId();
        //
        this.sequence = event.sequence();
        //
        this.relayed = event.relayed();
        this.propagate = event.propagate();
        this.deleted = false;
    }

    public Event toEvent() {
        //
        try {
            Event event = (Event) JsonUtil.fromJson(this.payload, Class.forName(this.eventType));
            event.eventId(this.eventId);
            event.sequence(this.sequence);
            event.relayed(this.relayed);
            event.propagate(this.propagate);
            event.correlationId(this.correlationId);
            return event;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException();
    }
}
