package dev.blynchik.quest_bot.model.content.expression.condition.params.impl.num;

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
public class UserNumParams implements ConditionParams {
    private ComparisonType comparisonType;
    private int val;
    private String paramKey;

    @Override
    public ConditionType getType() {
        return ConditionType.USER_NUM;
    }
}
