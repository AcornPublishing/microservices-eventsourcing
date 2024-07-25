package io.cosmos.notification.command;

import io.cosmos.eventsourcing.Event;
import io.cosmos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutAlert extends Event {
    //
    private String id;
    private String message;
    private String redirectPath;

    @Override
    public String toString() {
        //
        return JsonUtil.toJson(this);
    }
}
