package dev.blynchik.quest_bot.model.content.condition.expression;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Data
public class ExpressionList implements Expression {
    private final ExpressionType type = ExpressionType.LIST;
    private BinaryLogicOperator operator;
    private List<Expression> expressions;

    public ExpressionList(BinaryLogicOperator operator, List<Expression> expressions) {
        this.operator = operator;
        this.expressions = expressions;
    }

    public ExpressionList(BinaryLogicOperator operator, Expression... expressions) {
        this.operator = operator;
        this.expressions = Arrays.asList(expressions);
    }
}
