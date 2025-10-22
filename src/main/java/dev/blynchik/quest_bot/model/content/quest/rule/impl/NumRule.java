package dev.blynchik.quest_bot.model.content.quest.rule.impl;

import dev.blynchik.quest_bot.model.content.quest.rule.Rule;
import dev.blynchik.quest_bot.model.content.quest.rule.RuleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumRule implements Rule {
    private final RuleType type = RuleType.NUM;
    private String name;
    private Integer value;
    private Integer min;
    private Integer max;
    private String outOfRuleDescr;

    @Override
    public RuleType getType() {
        return type;
    }
}
