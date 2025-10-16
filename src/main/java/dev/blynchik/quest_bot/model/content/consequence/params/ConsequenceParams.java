package dev.blynchik.quest_bot.model.content.consequence.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartEventParam.class, name = "NEXT_EVENT")
})
public interface ConsequenceParams {
    ConsequenceType getType();
}
