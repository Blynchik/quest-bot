package dev.blynchik.quest_bot.service.message.message;

import dev.blynchik.quest_bot.exception.exception.IllegalMessageContentException;
import dev.blynchik.quest_bot.exception.exception.IllegalMessageTypeException;
import dev.blynchik.quest_bot.model.chat.ChatStore;
import dev.blynchik.quest_bot.model.player.PlayerStore;
import dev.blynchik.quest_bot.service.model.ChatService;
import dev.blynchik.quest_bot.service.model.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

import static dev.blynchik.quest_bot.config.bot.util.CallbackType.RANDOM_QUEST;
import static dev.blynchik.quest_bot.config.bot.util.CallbackType.START;
import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.*;
import static dev.blynchik.quest_bot.model.chat.ChatStateStore.WAITING_NAME;
import static dev.blynchik.quest_bot.model.chat.ChatStateStore.WAITING_QUEST;

@Component
@Slf4j
public class MessageHandler {
    private final ChatService chatService;
    private final PlayerService playerService;

    @Autowired
    public MessageHandler(ChatService chatService,
                          PlayerService playerService) {
        this.chatService = chatService;
        this.playerService = playerService;
    }

    public SendMessage handle(Message msg) {
        checkMessageContent(msg);
        Optional<ChatStore> chat = chatService.getByTgIdOpt(msg.getChatId());
        if (chat.isPresent() && chat.get().getChatState().equals(WAITING_NAME) && chat.get().getPlayer() == null) {
            if (msg.getText().isEmpty() || msg.getText().length() > 20 || msg.getText().isBlank()) {
                throw new IllegalMessageContentException("Слишком длинное или невалидное имя.");
            }
            PlayerStore player = playerService.create(
                    new PlayerStore(msg.getText()));
            chatService.updatePlayer(chat.get(), player);
            chatService.updateChatState(chat.get(), WAITING_QUEST);
            return createMessageWithButton(msg.getChatId(), "Отлично, рейнджер %s! Приступайте к выполнению задания.".formatted(msg.getText()),
                    createInlineKeyboardButtonRows(
                            createInlineKeyboardButton("Случайный квест", RANDOM_QUEST)
                    ));

        } else if (chat.isPresent() && chat.get().getChatState().equals(WAITING_QUEST)) {
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
