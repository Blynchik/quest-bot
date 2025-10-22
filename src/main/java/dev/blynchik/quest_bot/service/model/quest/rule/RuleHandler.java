package dev.blynchik.quest_bot.service.model.quest.rule;

import dev.blynchik.quest_bot.model.content.quest.rule.Rule;

public interface RuleHandler<T extends Rule> {
    String customizeValue(T rule, String value);
}
