package io.cosmos.saga.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SagaTimeExpired {
    //
    private String correlationId;
    private String sagaType;
}
