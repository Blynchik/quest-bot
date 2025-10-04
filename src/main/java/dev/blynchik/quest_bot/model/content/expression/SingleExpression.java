package dev.blynchik.quest_bot.model.content.expression;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SingleExpression implements Expression {
    private final ExpressionType type = ExpressionType.SINGLE;
    private Long conditionId;
}
