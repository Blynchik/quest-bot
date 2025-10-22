package dev.blynchik.quest_bot.model.content.quest.rule.impl;

import dev.blynchik.quest_bot.model.content.quest.rule.RuleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumRuleWithDescr extends NumRule {
    private final RuleType type = RuleType.NUM_WITH_DESCR;
    private Map<Integer, String> descrMap;

    public NumRuleWithDescr(String name, Integer value, Integer min, Integer max, Map<Integer, String> descrMap, String outOfRuleDescr) {
        super(name, value, min, max, outOfRuleDescr);
        this.descrMap = descrMap;
    }

    @Override
    public RuleType getType() {
        return type;
    }
}
