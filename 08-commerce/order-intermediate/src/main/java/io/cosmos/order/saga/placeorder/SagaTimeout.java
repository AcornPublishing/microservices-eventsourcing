package io.cosmos.order.saga.placeorder;

import io.cosmos.order.saga.event.SagaTimeExpired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Calendar;
import java.util.Date;

public class SagaTimeout implements Runnable {
    //
    private final String correlationId;
    private final String sagaType;
    private final ApplicationEventPublisher applicationEventPublisher;

    public SagaTimeout(String correlationId,
                       String sagaType,
                       ApplicationEventPublisher applicationEventPublisher) {
        //
        this.correlationId = correlationId;
        this.sagaType = sagaType;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void run() {
        //
        SagaTimeExpired sagaTimeExpired = new SagaTimeExpired(this.correlationId, sagaType);
        this.applicationEventPublisher.publishEvent(sagaTimeExpired);
    }

    public static Date expireTime(int seconds) {
        //
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, seconds);

        return calendar.getTime();
    }
}


