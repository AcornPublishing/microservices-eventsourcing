package io.cosmos.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {
    //
    private String eventId;
    //
    private String typeName;
    private String payload;
}
