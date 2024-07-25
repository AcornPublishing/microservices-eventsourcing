package io.cosmos.eventsourcing;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cosmos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntermediateEvent {

    private String id;
    private String typeName;
    private ObjectNode payload;
    private String revision;

    public Event toEvent() {
        Event result = null;

        try {
            Class clazz = Class.forName(this.typeName);
            result = (Event) JsonUtil.fromJson(JsonUtil.toJson(payload), clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();;
        }

        return result;
    }
}

