package dev.blynchik.quest_bot.service.model.quest.rule;

import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NumRuleHandler implements RuleHandler<NumRule> {

    @Override
    public String customizeValue(NumRule rule, String value) {
        log.info("Customize value: {} by rule: {}", value, rule);
        return value;
    }
}
