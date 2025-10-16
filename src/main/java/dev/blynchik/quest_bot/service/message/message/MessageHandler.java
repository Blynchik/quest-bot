package dev.blynchik.quest_bot.service.message.message;

import dev.blynchik.quest_bot.exception.exception.IllegalMessageContentException;
import dev.blynchik.quest_bot.exception.exception.IllegalMessageTypeException;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

import static dev.blynchik.quest_bot.config.bot.util.CallbackType.RANDOM_QUEST;
import static dev.blynchik.quest_bot.config.bot.util.CallbackType.START;
import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.*;
import static dev.blynchik.quest_bot.model.user.UserState.WAITING_NAME;
import static dev.blynchik.quest_bot.model.user.UserState.WAITING_QUEST;

@Component
@Slf4j
public class MessageHandler {
    private final UserService userService;

    @Autowired
    public MessageHandler(UserService userService) {
        this.userService = userService;
    }

    public SendMessage handle(Message msg) {
        checkMessageContent(msg);
        Optional<UserStore> chat = userService.getByChatIdOpt(msg.getChatId());
        if (chat.isPresent() && chat.get().getState().equals(WAITING_NAME) && chat.get().getName() == null) {
            if (msg.getText().isEmpty() || msg.getText().length() > 20 || msg.getText().isBlank()) {
                throw new IllegalMessageContentException("Слишком длинное или невалидное имя.");
            }
            userService.createPlayer(chat.get(), msg.getText());
            return createMessageWithButton(msg.getChatId(), "Отлично, рейнджер %s! Приступайте к выполнению задания.".formatted(msg.getText()),
                    createInlineKeyboardButtonRows(
                            createInlineKeyboardButton("Случайный квест", RANDOM_QUEST)
                    ));

        } else if (chat.isPresent() && chat.get().getState().equals(WAITING_QUEST)) {
            return createMessageWithButton(msg.getChatId(), "Почему не на задании, рейнджер?",
                    createInlineKeyboardButtonRows(
                            createInlineKeyboardButton("Случайный квест", RANDOM_QUEST)
                    ));

        } else if (chat.isEmpty()) {
            return createMessageWithButton(msg.getChatId(), "Войди через команду /start, рейнджер!",
                    createInlineKeyboardButtonRows(
                            createInlineKeyboardButton("Старт", START)
                    ));
        }

        throw new IllegalMessageTypeException("Сделай выбор или отправь команду!");
    }

    private void checkMessageContent(Message msg) {
        if (!msg.hasText() || msg.getText().trim().isEmpty()) {
            log.error("Received empty message from chat tg id: {}", msg.getChat().getId());
            throw new IllegalMessageContentException("Отправлено пустое сообщение.");
        }
    }
}
