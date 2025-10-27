package dev.blynchik.quest_bot.service.model.content.expression.condition.params;

import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.num.UserNumParams;
import dev.blynchik.quest_bot.model.user.UserStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NumParamsHandler implements ConditionParamsHandler<UserNumParams> {

    @Override
    public boolean handle(UserNumParams params, UserStore userStore) {
        log.info("Handle num params: {}", params);
        switch (params.getComparisonType()) {
            case LESS -> {
                return getComparisonValue(userStore, params.getParamKey()) < params.getVal();
            }
            case MORE -> {
                return getComparisonValue(userStore, params.getParamKey()) > params.getVal();
            }
            case EQ -> {
                return getComparisonValue(userStore, params.getParamKey()) == params.getVal();
            }
            case LESS_EQ -> {
                return getComparisonValue(userStore, params.getParamKey()) <= params.getVal();
            }
            case MORE_EQ -> {
                return getComparisonValue(userStore, params.getParamKey()) >= params.getVal();
            }
        }
        throw new IllegalArgumentException("Not valid comparison type of num params");
    }

    private int getComparisonValue(UserStore userStore, String key) {
        return Integer.parseInt(userStore.getCustom().getRuleStates().get(key));
    }
}
