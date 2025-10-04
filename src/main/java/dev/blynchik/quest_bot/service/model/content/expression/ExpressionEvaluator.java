package dev.blynchik.quest_bot.service.model.content.expression;

import dev.blynchik.quest_bot.model.content.expression.*;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionStore;
import dev.blynchik.quest_bot.service.model.content.ConditionService;
import dev.blynchik.quest_bot.service.model.content.expression.condition.params.ConditionParamsHandlerPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ExpressionEvaluator {
    private final ConditionService conditionService;
    private final ConditionParamsHandlerPool handlerPool;

    @Autowired
    public ExpressionEvaluator(ConditionService conditionService, ConditionParamsHandlerPool handlerPool) {
        this.conditionService = conditionService;
        this.handlerPool = handlerPool;
    }

    public boolean evaluate(Expression expression) {
        log.info("Starting resolve expression");
        switch (expression.getType()) {
            case SINGLE -> {
                return evaluateSingle(expression);
            }
            case NOT -> {
                return evaluateNot(expression);
            }
            case NODE -> {
                return evaluateNode(expression);
            }
            case LIST -> {
                return evaluateList(expression);
            }
        }
        throw new RuntimeException("Здесь не так");
    }

    private boolean evaluateSingle(Expression exp) {
        log.info("Evaluating single expression: {}", exp);
        SingleExpression se = (SingleExpression) exp;
        ConditionStore condition = conditionService.getById(se.getConditionId());
        return handlerPool.getHandler(condition.getType())
                .handle(condition.getParams());
    }

    private boolean evaluateNot(Expression exp) {
        log.info("Evaluating not expression: {}", exp);
        NotExpression ne = (NotExpression) exp;
        return !evaluate(ne.getInner());
    }

    private boolean evaluateNode(Expression exp) {
        log.info("Evaluating node expression: {}", exp);
        NodeExpression node = (NodeExpression) exp;
        boolean right = evaluate(node.getRight());
        boolean left = evaluate(node.getLeft());
        if (node.getOperator().equals(BinaryLogicOperator.OR)) {
            return right || left;
        } else {
            return right && left;
        }
    }

    private boolean evaluateList(Expression exp) {
        log.info("Evaluating list expression: {}", exp);
        ExpressionList el = (ExpressionList) exp;
        List<Expression> list = el.getExpressions();
        boolean result = evaluate(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if (el.getOperator().equals(BinaryLogicOperator.OR)) {
                result = result || evaluate(list.get(i));
            } else {
                result = result && evaluate(list.get(i));
            }
        }
        return result;
    }
}
