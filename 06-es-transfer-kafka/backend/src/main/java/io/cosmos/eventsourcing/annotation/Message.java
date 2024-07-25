package io.cosmos.eventsourcing.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Message {
    String topic() default "";
}

