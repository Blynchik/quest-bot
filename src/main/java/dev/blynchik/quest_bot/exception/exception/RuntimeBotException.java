package dev.blynchik.quest_bot.exception.exception;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RuntimeBotException extends RuntimeException {
    public RuntimeBotException(TelegramApiException e) {
        super(e);
    }
}
