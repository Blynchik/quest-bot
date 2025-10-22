package dev.blynchik.quest_bot.model.user;

import dev.blynchik.quest_bot.model.content.quest.rule.Rule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerCustom {
    private String ranger;
    private String planet;
    private String system;
    private String reward;
    private String tillDate;
    private Map<String, Rule> rules;
    private Map<String, String> ruleStates;
}
