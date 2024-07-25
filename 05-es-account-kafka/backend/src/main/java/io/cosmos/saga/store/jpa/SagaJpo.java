package io.cosmos.saga.store.jpa;

import io.cosmos.saga.core.EventSourcedSaga;
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
@Table(name = "TB_SAGA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SagaJpo {
    //
    @Id
    private String id;
    private String type;
    private long sequence;
    private boolean inSaga;
    @Version
    private long version;

    public SagaJpo(EventSourcedSaga saga) {
        //
        this.id = saga.identifier();;
        this.type = saga.getClass().getName();
        this.sequence = saga.sequence();
        this.inSaga = isInSaga();
        this.version = saga.getVersion();
    }

    public <T> T toSaga() {
        try {
            return (T) JsonUtil.fromJson("{}", Class.forName(this.type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException();
    }
}
