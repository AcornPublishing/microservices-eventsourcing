package io.cosmos.message;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountStreamListener {
    //
    @Value("${spring.application.name}")
    private String topic;
    @Value("${spring.application.name}")
    private String serviceName;
    //
    private Logger logger = LoggerFactory.getLogger(AccountStreamListener.class);
    //
    private final ApplicationEventPublisher eventPublisher;

    public AccountStreamListener(ApplicationEventPublisher eventPublisher) {
        //
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(topics = "${broker.topic}", groupId = "${spring.application.name}")
    public void on(String message) {
        //
        logger.info("Stream received: " + message.toString());

        //
        KafkaMessage kafkaMessage = JsonUtil.fromJson(message, KafkaMessage.class);

        try {
            Class<Event> clazz = (Class<Event>) Class.forName(kafkaMessage.getTypeName());
            Event event = JsonUtil.fromJson(kafkaMessage.getPayload(), clazz);
            eventPublisher.publishEvent(event);
        } catch (ClassNotFoundException e) {
            logger.warn(String.format("could not find %s class", kafkaMessage.getTypeName()));
        }
    }
}
