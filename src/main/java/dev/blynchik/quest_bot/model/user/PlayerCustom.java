package dev.blynchik.quest_bot.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerCustom {
    private String ranger;
    private String planet;
    private String system;
    private String reward;
    private String tillDate;
}
