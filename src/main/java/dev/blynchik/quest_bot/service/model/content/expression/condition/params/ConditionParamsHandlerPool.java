package dev.blynchik.quest_bot.service.model.content.expression.condition.params;

import dev.blynchik.quest_bot.model.content.expression.condition.ConditionType;
import dev.blynchik.quest_bot.model.content.expression.condition.params.ConditionParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ConditionParamsHandlerPool {
    private final Map<ConditionType, ConditionParamsHandler<? extends ConditionParams>> pool = new HashMap<>();

    @Autowired
    public ConditionParamsHandlerPool(AlwaysParamsHandler alwaysParamsHandler,
                                      ChanceParamsHandler chanceParamsHandler,
                                      NumParamsHandler numParamsHandler) {
        pool.put(ConditionType.ALWAYS, alwaysParamsHandler);
        pool.put(ConditionType.CHANCE, chanceParamsHandler);
        pool.put(ConditionType.USER_NUM, numParamsHandler);
    }

    @SuppressWarnings("unchecked")
    public <T extends ConditionParams> ConditionParamsHandler<T> getHandler(ConditionType type) {
        log.info("Get handler for: {} from condition params handlers pool", type);
        return (ConditionParamsHandler<T>) pool.get(type);
    }
}
