package dev.blynchik.quest_bot.model.content.expression.condition.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlwaysParams.class, name = "ALWAYS"),
        @JsonSubTypes.Type(value = ChanceParams.class, name = "CHANCE"),
})
public interface ConditionParams {
    ConditionType getType();
}
