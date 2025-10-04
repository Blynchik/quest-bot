package dev.blynchik.quest_bot.config.bot;

import dev.blynchik.quest_bot.exception.exception.IllegalMessageTypeException;
import dev.blynchik.quest_bot.exception.exception.RuntimeBotException;
import dev.blynchik.quest_bot.exception.handler.ExceptionHandler;
import dev.blynchik.quest_bot.service.message.callback.CallbackHandler;
import dev.blynchik.quest_bot.service.message.message.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Set;

import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.createMessage;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final Set<BotCommand> commands;
    private final MessageHandler messageHandler;
    private final ExceptionHandler exceptionHandler;
    private final CallbackHandler callbackHandler;

    @Autowired
    public TelegramBot(Environment environment, Set<BotCommand> commands, MessageHandler messageHandler,
                       ExceptionHandler exceptionHandler, CallbackHandler callbackHandler) {
        super(environment.getProperty("BOT_TOKEN"));
        this.botUsername = environment.getProperty("BOT_USERNAME");
        this.commands = commands;
        this.messageHandler = messageHandler;
        this.exceptionHandler = exceptionHandler;
        this.callbackHandler = callbackHandler;
        registerAll(commands.toArray(new BotCommand[0]));
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        try {
            if (update.hasMessage()) {
                replyToUser(
                        messageHandler.handle(update.getMessage()));
            } else if (update.hasCallbackQuery()) {
                replyToUser(
                        callbackHandler.handle(update.getCallbackQuery()));
            } else {
                replyToUser(
                        createMessage(extractChatId(update), "Неизвестный тип сообщения."));
                throw new IllegalMessageTypeException("Неизвестный тип сообщения.");
            }
        } catch (Exception ex) {
            exceptionHandler.handle(this, extractChatId(update), ex);
        }
    }

    public void replyToUser(SendMessage sendMessage) {
        log.info("Reply to chat tg id: {} message: {}", sendMessage.getChatId(), sendMessage.getText());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Exception sending message: %s".formatted(sendMessage.getChatId()));
            throw new RuntimeBotException(e);
        }
    }

    private Long extractChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        log.error("Unknown message type");
        throw new IllegalMessageTypeException("Неизвестный тип сообщения.");
    }
}
