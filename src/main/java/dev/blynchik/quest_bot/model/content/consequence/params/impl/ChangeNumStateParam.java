package dev.blynchik.quest_bot.model.content.consequence.params.impl;

import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;
import dev.blynchik.quest_bot.model.content.consequence.params.ConsequenceParams;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeNumStateParam implements ConsequenceParams {
    private final ConsequenceType type = ConsequenceType.CHANGE_NUM_STATE;
    private String key;
    private int value;

    public ChangeNumStateParam(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public ConsequenceType getType() {
        return this.type;
    }
}
