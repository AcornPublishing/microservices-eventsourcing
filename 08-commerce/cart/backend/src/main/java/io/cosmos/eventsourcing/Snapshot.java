package io.cosmos.eventsourcing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Snapshot {
    //
    private String payload;
    private long sequence;
    private long time;
    //
    private long quotient;
    private long remainder;

    public Snapshot(String payload, long time, long sequence) {
        //
        this.payload = payload;
        this.time = time;
        this.sequence = sequence;
        this.quotient = sequence / 3;
        this.remainder = sequence % 3;
    }

    public boolean isExpired(long sequence) {
        //
        return sequence % 3 == 0 && this.quotient != sequence / 3;
    }
}
