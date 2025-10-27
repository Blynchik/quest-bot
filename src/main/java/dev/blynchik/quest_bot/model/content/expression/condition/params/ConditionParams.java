package dev.blynchik.quest_bot.model.content.expression.condition.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionType;
import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.AlwaysParams;
import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.ChanceParams;
import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.num.UserNumParams;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlwaysParams.class, name = "ALWAYS"),
        @JsonSubTypes.Type(value = ChanceParams.class, name = "CHANCE"),
        @JsonSubTypes.Type(value = UserNumParams.class, name = "USER_NUM")
})
public interface ConditionParams {
    ConditionType getType();
}
