package dev.blynchik.quest_bot.service.model.content.expression.condition.params;

import dev.blynchik.quest_bot.model.content.expression.condition.params.ChanceParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChanceParamsHandler implements ConditionParamsHandler<ChanceParams> {
    @Override
    public boolean handle(ChanceParams params) {
        double result = Math.random();
        log.info("Handle chance: {} and result: {}", params.getChance(), result);
        return result > params.getChance();
    }
}
