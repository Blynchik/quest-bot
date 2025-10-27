package dev.blynchik.quest_bot.service.model.content.expression;

import dev.blynchik.quest_bot.exception.exception.IllegalExpressionTypeException;
import dev.blynchik.quest_bot.model.content.expression.BinaryLogicOperator;
import dev.blynchik.quest_bot.model.content.expression.Expression;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionStore;
import dev.blynchik.quest_bot.model.content.expression.impl.ExpressionList;
import dev.blynchik.quest_bot.model.content.expression.impl.NodeExpression;
import dev.blynchik.quest_bot.model.content.expression.impl.NotExpression;
import dev.blynchik.quest_bot.model.content.expression.impl.SingleExpression;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.service.model.content.ConditionService;
import dev.blynchik.quest_bot.service.model.content.expression.condition.params.ConditionParamsHandlerPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExpressionEvaluator {
    private final ConditionService conditionService;
    private final ConditionParamsHandlerPool handlerPool;

    @Autowired
    public ExpressionEvaluator(ConditionService conditionService, ConditionParamsHandlerPool handlerPool) {
        this.conditionService = conditionService;
        this.handlerPool = handlerPool;
    }

    public boolean evaluate(Expression expression, UserStore userStore) {
        log.info("Starting resolve expression");
        switch (expression.getType()) {
            case SINGLE -> {
                return evaluateSingle(expression, userStore);
            }
            case NOT -> {
                return evaluateNot(expression, userStore);
            }
            case NODE -> {
                return evaluateNode(expression, userStore);
            }
            case LIST -> {
                return evaluateList(expression, userStore);
            }
        }
        throw new IllegalExpressionTypeException("Неверный тип выражения");
    }

    private boolean evaluateSingle(Expression exp, UserStore userStore) {
        log.info("Evaluating single expression: {}", exp);
        SingleExpression se = (SingleExpression) exp;
        ConditionStore condition = conditionService.getById(se.getConditionId());
        return handlerPool.getHandler(condition.getType())
                .handle(condition.getParams(), userStore);
    }

    private boolean evaluateNot(Expression exp, UserStore userStore) {
        log.info("Evaluating not expression: {}", exp);
        NotExpression ne = (NotExpression) exp;
        return !evaluate(ne.getInner(), userStore);
    }

    private boolean evaluateNode(Expression exp, UserStore userStore) {
        log.info("Evaluating node expression: {}", exp);
        NodeExpression node = (NodeExpression) exp;
        boolean right = evaluate(node.getRight(), userStore);
        boolean left = evaluate(node.getLeft(), userStore);
        if (node.getOperator().equals(BinaryLogicOperator.OR)) {
            return right || left;
        } else {
            return right && left;
        }
    }

    private boolean evaluateList(Expression exp, UserStore userStore) {
        log.info("Evaluating list expression: {}", exp);
        ExpressionList el = (ExpressionList) exp;
        List<Expression> list = el.getExpressions();
        boolean result = evaluate(list.get(0), userStore);
        for (int i = 1; i < list.size(); i++) {
            if (el.getOperator().equals(BinaryLogicOperator.OR)) {
                result = result || evaluate(list.get(i), userStore);
            } else {
                result = result && evaluate(list.get(i), userStore);
            }
        }
        return result;
    }
}
