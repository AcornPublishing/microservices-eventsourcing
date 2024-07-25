package io.cosmos.saga.time;

import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Calendar;
import java.util.Date;
@Getter
public class SagaTimeout implements Runnable {
    //
    private String correlationId;
    private String sagaType;
    private Date expireTime;
    private boolean complete;
    private final ApplicationEventPublisher eventPublisher;

    public SagaTimeout(String correlationId,
                       String sagaType,
                       Date expireTime,
                       ApplicationEventPublisher eventPublisher) {
        //
        this.correlationId = correlationId;
        this.sagaType = sagaType;
        this.expireTime = expireTime;
        this.complete = false;
        this.eventPublisher = eventPublisher;
    }

    public void complete() {
        //
        this.complete = true;
    }

    public static Date currentTime() {
        //
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    @Override
    public void run() {
        //
        SagaTimeExpired sagaTimeExpired = new SagaTimeExpired(this.correlationId, sagaType);
        this.eventPublisher.publishEvent(sagaTimeExpired);
    }

    public boolean expired() {
        //
        return !this.complete && System.currentTimeMillis() > this.expireTime.getTime();
    }

    public static Date expireTime(int seconds) {
        //
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, seconds);

        return calendar.getTime();
    }
}
