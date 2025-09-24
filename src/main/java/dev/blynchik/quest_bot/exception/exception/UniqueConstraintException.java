package dev.blynchik.quest_bot.exception.exception;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(String msg) {
        super(msg);
    }
}
