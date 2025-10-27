package dev.blynchik.quest_bot.service.model.content.expression.condition.params;

import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.AlwaysParams;
import dev.blynchik.quest_bot.model.user.UserStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AlwaysParamsHandler implements ConditionParamsHandler<AlwaysParams> {

    @Override
    public boolean handle(AlwaysParams params, UserStore userStore) {
        log.info("Handle always: {}", params.isAlways());
        return params.isAlways();
    }
}
