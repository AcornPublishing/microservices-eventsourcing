package io.cosmos.saga.store.jpa;

import io.cosmos.saga.time.SagaTimeout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TB_SAGA_TIMEOUT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SagaTimeoutJpo {
    //
    @Id
    private String correlationId;
    private String sagaType;
    private Date expireTime;
    private boolean complete;

    public SagaTimeoutJpo(SagaTimeout sagaTimeout) {
        //
        this.correlationId = sagaTimeout.getCorrelationId();
        this.sagaType = sagaTimeout.getSagaType();
        this.expireTime = sagaTimeout.getExpireTime();
        this.complete = sagaTimeout.isComplete();
    }

    public SagaTimeout toSagaTimeout(ApplicationEventPublisher eventPublisher) {
        //
        return new SagaTimeout(this.correlationId, this.sagaType, this.expireTime, eventPublisher);
    }
}
