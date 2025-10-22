package dev.blynchik.quest_bot.service.model.quest.rule;

import dev.blynchik.quest_bot.model.content.quest.rule.Rule;
import dev.blynchik.quest_bot.model.content.quest.rule.RuleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RuleHandlerPool {
    private final Map<RuleType, RuleHandler<? extends Rule>> pool = new HashMap<>();

    @Autowired
    public RuleHandlerPool(NumRuleHandler numRuleHandler,
                           NumRuleWithDescrHandler numRuleWithDescrHandler) {
        this.pool.put(RuleType.NUM, numRuleHandler);
        this.pool.put(RuleType.NUM_WITH_DESCR, numRuleWithDescrHandler);
    }

    @SuppressWarnings("unchecked")
    public <T extends Rule> RuleHandler<T> getHandler(Rule rule) {
        log.info("Get handler for: {} from rule handlers pool", rule.getType());
        return (RuleHandler<T>) pool.get(rule.getType());
    }
}
