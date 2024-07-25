package io.cosmos.eventsourcing.store.jpa;

import io.cosmos.eventsourcing.core.EventSourcedAggregate;
import io.cosmos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "TB_AGGREGATE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateJpo {
    //
    @Id
    private String id;
    private String type;
    private long sequence;
    private boolean deleted;
    @Version
    private long version;

    public AggregateJpo(EventSourcedAggregate aggregate) {
        //
        this.id = aggregate.identifier();;
        this.type = aggregate.getClass().getName();
        this.sequence = aggregate.sequence();
        this.version = aggregate.getVersion();
        this.deleted = aggregate.deleted();
    }

    public <T> T toAggregate() {
        try {
            return (T) JsonUtil.fromJson("{}", Class.forName(this.type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException();
    }
}
