package io.cosmos.transfer.saga.reconstitute;

import io.cosmos.saga.store.SagaTimeoutStore;
import io.cosmos.saga.time.SagaTimeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SagaReconstituter {
    //
    private static final Logger logger = LoggerFactory.getLogger(SagaReconstituter.class);

    private final TaskScheduler taskScheduler;
    private final SagaTimeoutStore sagaTimeoutStore;
    private final ApplicationEventPublisher eventPublisher;

    public SagaReconstituter(TaskScheduler taskScheduler,
                             SagaTimeoutStore sagaTimeoutStore,
                             ApplicationEventPublisher eventPublisher) {
        //
        this.taskScheduler = taskScheduler;
        this.sagaTimeoutStore = sagaTimeoutStore;
        this.eventPublisher = eventPublisher;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void constitute() {
        //
        List<SagaTimeout> sagaTimeouts = this.sagaTimeoutStore.retrievePendingTimeout();
        logger.info("SagaTimeout size is {}", sagaTimeouts.size());

        sagaTimeouts.stream().forEach(timeout -> {
            if (timeout.expired()) {
                logger.info("Saga({}-{}) is expired.", timeout.getSagaType(), timeout.getCorrelationId());

                Date expireTime = SagaTimeout.currentTime();
                SagaTimeout sagaTimeout = new SagaTimeout(timeout.getCorrelationId(), timeout.getSagaType(),
                        expireTime, this.eventPublisher);
                this.taskScheduler.schedule(sagaTimeout, expireTime);
            } else {
                SagaTimeout sagaTimeout = new SagaTimeout(timeout.getCorrelationId(), timeout.getSagaType(),
                        timeout.getExpireTime(), this.eventPublisher);
                this.taskScheduler.schedule(sagaTimeout, timeout.getExpireTime());
            }
        });
    }
}
