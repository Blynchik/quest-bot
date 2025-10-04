package dev.blynchik.quest_bot.model.content.expression.condition.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChanceParams implements ConditionParams {
    private double chance;

    @Override
    public ConditionType getType() {
        return ConditionType.CHANCE;
    }
}
