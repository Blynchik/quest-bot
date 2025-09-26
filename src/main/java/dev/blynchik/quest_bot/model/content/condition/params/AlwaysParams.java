package dev.blynchik.quest_bot.model.content.condition.params;

import dev.blynchik.quest_bot.model.content.condition.ConditionType;
import lombok.Data;

@Data
public class AlwaysParams implements ConditionParams {
    private final boolean isAlways = true;

    @Override
    public ConditionType getType() {
        return ConditionType.ALWAYS;
    }
}
