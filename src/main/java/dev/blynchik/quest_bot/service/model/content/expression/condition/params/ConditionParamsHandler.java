package dev.blynchik.quest_bot.service.model.content.expression.condition.params;

import dev.blynchik.quest_bot.model.content.expression.condition.params.ConditionParams;
import dev.blynchik.quest_bot.model.user.UserStore;

public interface ConditionParamsHandler<T extends ConditionParams> {
    boolean handle(T params, UserStore userStore);
}
