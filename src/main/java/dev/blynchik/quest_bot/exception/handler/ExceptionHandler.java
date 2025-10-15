package dev.blynchik.quest_bot.exception.handler;

import dev.blynchik.quest_bot.exception.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.createMessage;

@Component
@Slf4j
public class ExceptionHandler {

    public void handle(AbsSender sender, Long tgChatId, Exception ex) {
        String commonMsg = "Ошибка! Что-то пошло не так! Попробуйте позже.";
        log.info("Handling exception: {}", ex.getMessage());

        if (ex instanceof UnexpectedCallbackException) {
            log.error("Logging UnexpectedCallbackException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof NotFoundException) {
            log.error("Logging NotFoundException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof IllegalButtonException) {
            log.error("Logging IllegalButtonException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof IllegalMessageTypeException) {
            log.error("Logging IllegalMessageTypeException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof IllegalCommandException) {
            log.error("Logging IllegalCommandException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof IllegalCallbackTypeException) {
            log.error("Logging IllegalCallbackTypeException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof IllegalMessageContentException) {
            log.error("Logging IllegalMessageContentException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof UniqueConstraintException) {
            log.error("Logging UniqueConstraintException", ex);
            replyToUser(sender, tgChatId, ex.getMessage());
            return;
        }

        if (ex instanceof NoPossibleResultException) {
            log.error("Logging NoPossibleResultException", ex);
            replyToUser(sender, tgChatId, commonMsg);
            return;
        }

        if (ex instanceof IllegalExpressionTypeException) {
            log.error("Logging IllegalExpressionTypeException", ex);
            replyToUser(sender, tgChatId, commonMsg);
            return;
        }

        if (ex instanceof IllegalConsequenceTypeException) {
            log.error("Logging IllegalConsequenceTypeException", ex);
            replyToUser(sender, tgChatId, commonMsg);
            return;
        }

        if (ex instanceof IllegalArgumentException) {
            log.error("Logging IllegalArgumentException", ex);
            replyToUser(sender, tgChatId, commonMsg);
            return;
        }

        if (ex instanceof RuntimeBotException) {
            log.error("Logging RuntimeBotException", ex);
            replyToUser(sender, tgChatId, commonMsg);
            return;
        }

        if (ex instanceof TelegramApiException) {
            log.error("Logging TelegramApiException", ex);
            replyToUser(sender, tgChatId, commonMsg);
            return;
        }

        if (ex instanceof RuntimeException) {
            log.error("Logging RuntimeException", ex);
            replyToUser(sender, tgChatId, commonMsg);
            return;
        }

        if (ex instanceof Exception) {
            log.error("Logging Exception", ex);
            replyToUser(sender, tgChatId, commonMsg);
        }
    }

    private void replyToUser(AbsSender sender, Long tgChatId, String text) {
        try {
            sender.execute(
                    createMessage(tgChatId, text));
        } catch (TelegramApiException e) {
            log.error("Exception thrown while sending exception info to chat tg id: %s".formatted(tgChatId));
            throw new RuntimeBotException(e);
        }
    }
}
