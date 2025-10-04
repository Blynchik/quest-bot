package dev.blynchik.quest_bot.model.content.expression;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NotExpression implements Expression {
    private final ExpressionType type = ExpressionType.NOT;
    private Expression inner;

    public NotExpression(Expression inner) {
        this.inner = inner;
    }
}