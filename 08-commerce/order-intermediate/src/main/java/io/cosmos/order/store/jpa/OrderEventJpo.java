package io.cosmos.order.store.jpa;

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
@Table(name = "TB_ORDER_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventJpo {
    //
    @Id
    private String eventId;
    //
    private String orderNo;
    //
    @Lob
    private String payload;
    private String eventType;
    //
    private long sequence;
    //
    private boolean relayed;
    private boolean propagate;
    private boolean deleted;
    private String revision;

    public OrderEventJpo(String orderNo, Event event) {
        //
        this.eventId = event.eventId();
        this.orderNo = orderNo;
        //
        this.payload = event.payload();
        this.eventType = event.typeName();
        //
        this.sequence = event.sequence();
        //
        this.relayed = event.relayed();
        this.propagate = event.propagate();
        this.deleted = false;
        //
        this.revision = event.revision();
    }

    public Event toEvent() {
        //
        try {
            Event event = (Event) JsonUtil.fromJson(this.payload, Class.forName(this.eventType));
            event.eventId(this.eventId);
            event.sequence(this.sequence);
            event.relayed(this.relayed);
            event.propagate(this.propagate);
            event.revision(this.revision);
            return event;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException();
    }
}
