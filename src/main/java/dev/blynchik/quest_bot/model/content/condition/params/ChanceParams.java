package dev.blynchik.quest_bot.model.content.condition.params;

import dev.blynchik.quest_bot.model.content.condition.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChanceParams implements ConditionParams {
    private double chance;

    @Override
    public ConditionType getType() {
        return ConditionType.CHANCE;
    }
}
