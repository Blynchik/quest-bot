package dev.blynchik.quest_bot.service.model.quest.rule;

import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRuleWithDescr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@Slf4j
public class NumRuleWithDescrHandler implements RuleHandler<NumRuleWithDescr> {
    @Override
    public String customizeValue(NumRuleWithDescr rule, String value) {
        log.info("Customize value: {} by rule: {}", value, rule);
        return rule.getDescrMap().get(
                rule.getDescrMap().keySet().stream()
                        .filter(leftBorder -> leftBorder <= Integer.parseInt(value))
                        .max(Comparator.naturalOrder())
                        .orElseThrow(() -> new IllegalArgumentException("The value has not correct mapping with rule")));
    }
}
