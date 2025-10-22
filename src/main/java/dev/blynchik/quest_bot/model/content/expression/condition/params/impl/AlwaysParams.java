package dev.blynchik.quest_bot.model.content.expression.condition.params.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionType;
import dev.blynchik.quest_bot.model.content.expression.condition.params.ConditionParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlwaysParams implements ConditionParams {
    private boolean isAlways = true;

    @Override
    public ConditionType getType() {
        return ConditionType.ALWAYS;
    }
}
