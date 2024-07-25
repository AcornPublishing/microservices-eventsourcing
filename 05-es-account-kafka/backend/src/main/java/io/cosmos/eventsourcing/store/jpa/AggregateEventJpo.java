package io.cosmos.eventsourcing.store.jpa;

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
@Table(name = "TB_AGGREGATE_EVENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateEventJpo {
    //
    @Id
    private String id;
    private String type;
    private String aggregateId;
    @Lob
    private String payload;
    private long sequence;
    private long time;
    private boolean relayed;
    private boolean propagate;
    private boolean deleted;
    private String correlationId;

    public AggregateEventJpo(String aggregateId, Event event) {
        //
        this.id = event.eventId();
        this.type = event.getClass().getName();
        this.aggregateId = aggregateId;
        this.payload = event.payload();
        this.sequence = event.sequence();
        this.time = event.time();
        this.relayed = event.relayed();
        this.propagate = event.propagate();
        this.correlationId = event.correlationId();
    }

    public Event toEvent() {
        //
        try {
            Event event = (Event) JsonUtil.fromJson(this.payload, Class.forName(this.type));
            event.eventId(this.id);
            event.sequence(this.sequence);
            event.time(this.time);
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
