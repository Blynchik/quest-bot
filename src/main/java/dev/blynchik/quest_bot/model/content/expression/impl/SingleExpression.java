package dev.blynchik.quest_bot.model.content.expression.impl;

import dev.blynchik.quest_bot.model.content.expression.Expression;
import dev.blynchik.quest_bot.model.content.expression.ExpressionType;
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
