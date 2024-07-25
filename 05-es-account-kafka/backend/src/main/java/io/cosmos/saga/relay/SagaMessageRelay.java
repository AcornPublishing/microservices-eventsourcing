package io.cosmos.saga.relay;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.message.KafkaMessage;
import io.cosmos.saga.store.SagaEventStore;
import io.cosmos.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SagaMessageRelay {
    //
    private static final Logger logger = LoggerFactory.getLogger(SagaMessageRelay.class);
    //
    private final SagaEventStore sagaEventStore;
    @Value("${spring.application.name}")
    private String serviceName;
    private final KafkaTemplate kafkaTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public SagaMessageRelay(SagaEventStore sagaEventStore,
                            KafkaTemplate kafkaTemplate,
                            ApplicationEventPublisher eventPublisher) {
        //
        this.sagaEventStore = sagaEventStore;
        //
        this.kafkaTemplate = kafkaTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 3000)
    public void publish() {
        List<Event> events = this.sagaEventStore.retrieveUnrelayedEvents();
        events.stream().forEach(event -> {

            this.eventPublisher.publishEvent(event);

            if (event.propagate()) {
                KafkaMessage kafkaMessage = new KafkaMessage(event.eventId(), event.getClass().getTypeName(), JsonUtil.toJson(event));
                Message message = MessageBuilder.withPayload(JsonUtil.toJson(kafkaMessage))
                        .setHeader(KafkaHeaders.TOPIC, this.serviceName)
                        .build();
                this.kafkaTemplate.send(message);
            }
            this.sagaEventStore.update(event);
        });
    }
}
