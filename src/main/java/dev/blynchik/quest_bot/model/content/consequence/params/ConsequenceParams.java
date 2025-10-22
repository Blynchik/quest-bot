package dev.blynchik.quest_bot.model.content.consequence.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.ChangeNumStateParam;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.StartEventParam;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartEventParam.class, name = "NEXT_EVENT"),
        @JsonSubTypes.Type(value = ChangeNumStateParam.class, name = "CHANGE_NUM_STATE"),
})
public interface ConsequenceParams {
    ConsequenceType getType();
}
