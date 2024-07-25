package io.cosmos.saga.store.jpa;

import io.cosmos.eventsourcing.core.Event;
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
@Table(name = "TB_SAGA_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SagaEventJpo {
    //
    @Id
    private String id;
    private String type;
    private String sagaId;
    @Lob
    private String payload;
    private long sequence;
    private long time;
    private boolean relayed;

    public SagaEventJpo(String sagaId, Event event) {
        //
        this.id = event.eventId();
        this.type = event.getClass().getName();
        this.sagaId = sagaId;
        this.payload = event.payload();
        this.sequence = event.sequence();
        this.time = event.time();
        this.relayed = event.relayed();
    }

    public Event toEvent() {
        //
        try {
            Event event = (Event) JsonUtil.fromJson(this.payload, Class.forName(this.type));
            event.eventId(this.id);
            event.sequence(this.sequence);
            event.time(this.time);
            event.relayed(this.relayed);
            return event;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException();
    }
}
