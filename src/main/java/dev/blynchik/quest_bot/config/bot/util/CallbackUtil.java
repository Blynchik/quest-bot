package dev.blynchik.quest_bot.config.bot.util;

public enum CallbackUtil {
    SEPARATOR("::");

    private final String replace;

    CallbackUtil(String replace) {
        this.replace = replace;
    }

    public String replace() {
        return replace;
    }
}
