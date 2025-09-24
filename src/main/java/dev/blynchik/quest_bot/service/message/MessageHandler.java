package dev.blynchik.quest_bot.service.message;

import dev.blynchik.quest_bot.exception.exception.IllegalMessageContentException;
import dev.blynchik.quest_bot.model.chat.ChatStore;
import dev.blynchik.quest_bot.model.player.PlayerStore;
import dev.blynchik.quest_bot.service.model.ChatService;
import dev.blynchik.quest_bot.service.model.PlayerService;
import dev.blynchik.quest_bot.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.createMessage;
import static dev.blynchik.quest_bot.model.chat.ChatStateStore.WAITING_NAME;
import static dev.blynchik.quest_bot.model.chat.ChatStateStore.WAITING_QUEST;

@Component
@Slf4j
public class MessageHandler {
    private final UserService userService;
    private final ChatService chatService;
    private final PlayerService playerService;

    @Autowired
    public MessageHandler(UserService userService, ChatService chatService,
                          PlayerService playerService) {
        this.userService = userService;
        this.chatService = chatService;
        this.playerService = playerService;
    }

    public SendMessage handle(Message msg) {
        checkMessageContent(msg);
        Optional<ChatStore> chat = chatService.getByTgIdOpt(msg.getChatId());

        if (chat.isPresent() && chat.get().getChatState().equals(WAITING_NAME) && chat.get().getPlayer() == null) {
            chatService.updateChatState(chat.get(), WAITING_QUEST);
            PlayerStore player = playerService.create(
                    new PlayerStore(msg.getText()));
            chatService.updatePlayer(chat.get(), player);
            return createMessage(msg.getChatId(), "Отлично, рейнджер %s! Приступайте к выполнению задания".formatted(msg.getText()));

        } else if (chat.isPresent() && chat.get().getChatState().equals(WAITING_QUEST)) {


        } else if (chat.isEmpty()) {
            return createMessage(msg.getChatId(), "Войди через команду /start, рейнджер!");

        }

        return createMessage(msg.getChatId(), "Сделай выбор или отправь команду!");
    }

    private void checkMessageContent(Message msg) {
        if (!msg.hasText() || msg.getText().trim().isEmpty()) {
            log.error("Received empty message from chat tg id: {}", msg.getChat().getId());
            throw new IllegalMessageContentException("Отправлено пустое сообщение");
        }
    }
}
