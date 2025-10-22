package dev.blynchik.quest_bot.model.content.quest.rule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRule;
import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRuleWithDescr;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NumRule.class, name = "NUM"),
        @JsonSubTypes.Type(value = NumRuleWithDescr.class, name = "NUM_WITH_DESCR")
})
public interface Rule {
    RuleType getType();
}
