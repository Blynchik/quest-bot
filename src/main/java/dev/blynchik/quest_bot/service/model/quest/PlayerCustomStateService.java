package dev.blynchik.quest_bot.service.model.quest;

import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import dev.blynchik.quest_bot.model.content.quest.rule.Rule;
import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRule;
import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRuleWithDescr;
import dev.blynchik.quest_bot.model.user.PlayerCustom;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.service.model.quest.rule.RuleHandlerPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class PlayerCustomStateService {
    private final RuleHandlerPool ruleHandlerPool;

    @Autowired
    public PlayerCustomStateService(RuleHandlerPool ruleHandlerPool) {
        this.ruleHandlerPool = ruleHandlerPool;
    }

    public Map<String, String> getStartState(UserStore player, QuestStore quest) {
        log.info("Preparing player's tg id: {} custom for quest id: {}", player.getTgUserId(), quest.getId());
        Map<String, String> startState = new HashMap<>();
        quest.getRule().keySet()
                .forEach(k -> {
                            Rule rule = quest.getRule().get(k);
                            switch (rule.getType()) {
                                case NUM_WITH_DESCR -> startState.put(k, ((NumRuleWithDescr) rule).getValue().toString());
                                case NUM -> startState.put(k, ((NumRule) rule).getValue().toString());
                                default -> throw new IllegalArgumentException("Неверный тип правила квеста.");
                            }
                        }
                );
        return startState;
    }

    public Map<String, String> customizeState(PlayerCustom custom) {
        log.info("Preparing to customize states: {} by rules: {}", custom.getRuleStates(), custom.getRules());
        Map<String, String> customized = new HashMap<>();
        custom.getRuleStates().keySet()
                .forEach(k -> customized.put(k,
                        ruleHandlerPool.getHandler(custom.getRules().get(k))
                                .customizeValue(custom.getRules().get(k), custom.getRuleStates().get(k))));
        return customized;
    }
}
