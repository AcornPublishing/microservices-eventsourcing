package io.cosmos.saga.relay;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.eventsourcing.core.MessageMapper;
import io.cosmos.message.KafkaMessage;
import io.cosmos.saga.store.SagaEventStore;
import io.cosmos.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SagaMessageRelay {
    //
    private static final Logger logger = LoggerFactory.getLogger(SagaMessageRelay.class);
    //
    private final SagaEventStore sagaEventStore;
    @Value("${spring.application.name}")
    private String serviceName;
    private final KafkaTemplate kafkaTemplate;
    private final ApplicationContext applicationContext;
    private List<MessageMapper> messageMappers;

    public SagaMessageRelay(SagaEventStore sagaEventStore,
                            KafkaTemplate kafkaTemplate,
                            ApplicationContext applicationContext) {
        //
        this.sagaEventStore = sagaEventStore;
        //
        this.kafkaTemplate = kafkaTemplate;
        this.applicationContext = applicationContext;
        //
        this.messageMappers = new ArrayList<>();

        Arrays.stream(applicationContext.getBeanNamesForType(MessageMapper.class))
                .forEach(name -> {
                    this.messageMappers.add(applicationContext.getBean(name, MessageMapper.class));
                });
    }

    @Scheduled(fixedDelay = 100)
    public void publish() {
        List<Event> events = this.sagaEventStore.retrieveUnrelayedEvents();
        events.stream().forEach(event -> {

            this.applicationContext.publishEvent(event);

            if (event.propagate()) {
                KafkaMessage kafkaMessage = new KafkaMessage(event.eventId(), event.getClass().getTypeName(), JsonUtil.toJson(event));
                Message message = MessageBuilder.withPayload(JsonUtil.toJson(kafkaMessage))
                        .setHeader(KafkaHeaders.TOPIC, this.serviceName)
                        .build();
                this.kafkaTemplate.send(message);

                // use @Message annotation
                /*
                messageMappers.stream().forEach(mapper -> {
                    Optional<io.cosmos.eventsourcing.core.Message> transformedMsg = mapper.map(event);
                    if (transformedMsg.isPresent()) {
                        io.cosmos.eventsourcing.annotation.Message annotation = transformedMsg.get().getClass().getAnnotation(io.cosmos.eventsourcing.annotation.Message.class);
                        if (annotation == null) {
                            logger.warn(String.format("%s has not a annotation.", "Message"));
                            return;
                        }
                        KafkaMessage kafkaMsg = new KafkaMessage(
                                UUID.randomUUID().toString(),
                                transformedMsg.get().getClass().getTypeName(),
                                JsonUtil.toJson(transformedMsg.get()));
                        Message transformedMessage = MessageBuilder
                                .withPayload(JsonUtil.toJson(kafkaMsg))
                                .setHeader(KafkaHeaders.TOPIC, annotation.topic())
                                .build();
                        this.kafkaTemplate.send(transformedMessage);
                    }
                });
                */
                // use Message interface's operation
                messageMappers.stream().forEach(mapper -> {
                    Optional<io.cosmos.eventsourcing.core.Message> transformedMsg = mapper.map(event);
                    if (transformedMsg.isPresent()) {
                        KafkaMessage kafkaMsg = new KafkaMessage(
                                UUID.randomUUID().toString(),
                                transformedMsg.get().getClass().getTypeName(),
                                JsonUtil.toJson(transformedMsg.get()));
                        Message transformedMessage = MessageBuilder
                                .withPayload(JsonUtil.toJson(kafkaMsg))
                                .setHeader(KafkaHeaders.TOPIC, transformedMsg.get().topic())
                                .build();
                        this.kafkaTemplate.send(transformedMessage);
                    }
                });
            }
            this.sagaEventStore.update(event);
        });
    }
}
