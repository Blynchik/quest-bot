package dev.blynchik.quest_bot.model.content.consequence.params;

import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StartEventParam implements ConsequenceParams {
    private final ConsequenceType type = ConsequenceType.START_EVENT;
    private Long eventId;

    public StartEventParam(Long eventId) {
        this.eventId = eventId;
    }
}
