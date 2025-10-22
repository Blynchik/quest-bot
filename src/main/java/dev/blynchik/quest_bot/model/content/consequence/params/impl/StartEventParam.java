package dev.blynchik.quest_bot.model.content.consequence.params.impl;

import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;
import dev.blynchik.quest_bot.model.content.consequence.params.ConsequenceParams;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StartEventParam implements ConsequenceParams {
    private final ConsequenceType type = ConsequenceType.NEXT_EVENT;
    private Long eventId;

    public StartEventParam(Long eventId) {
        this.eventId = eventId;
    }
}
