package dev.blynchik.quest_bot.config.bot.util;

public enum TextCustomizerUtil {
    ACTIONS_SEPARATOR("\n\nВарианты\n=============================\n"),
    CUSTOM_STATE_SEPARATOR("\n\nСостояние\n=============================\n"),
    RANGER("ranger_name"),
    PLANET("planet_name"),
    SYSTEM("system_name"),
    REWARD("reward_value"),
    DATE("date");

    private final String replace;

    TextCustomizerUtil(String replace) {
        this.replace = replace;
    }

    public String replace() {
        return replace;
    }
}
