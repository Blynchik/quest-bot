package dev.blynchik.quest_bot.model.content.condition.expression;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NodeExpression implements Expression {
    private final ExpressionType type = ExpressionType.NODE;
    private Expression left;
    private BinaryLogicOperator operator;
    private Expression right;
}
