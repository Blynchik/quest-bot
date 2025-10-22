package dev.blynchik.quest_bot.model.content.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.blynchik.quest_bot.model.content.expression.impl.ExpressionList;
import dev.blynchik.quest_bot.model.content.expression.impl.NodeExpression;
import dev.blynchik.quest_bot.model.content.expression.impl.NotExpression;
import dev.blynchik.quest_bot.model.content.expression.impl.SingleExpression;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleExpression.class, name = "SINGLE"),
        @JsonSubTypes.Type(value = NotExpression.class, name = "NOT"),
        @JsonSubTypes.Type(value = NodeExpression.class, name = "NODE"),
        @JsonSubTypes.Type(value = ExpressionList.class, name = "LIST")
})
public interface Expression {
    ExpressionType getType();
}
