package dev.blynchik.quest_bot.service.message.command;

import dev.blynchik.quest_bot.model.chat.ChatStateStore;
import dev.blynchik.quest_bot.model.chat.ChatStore;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.createMessage;

@Component
@Slf4j
public class CommandHandler {
    private final UserService userService;

    @Autowired
    public CommandHandler(UserService userService) {
        this.userService = userService;
    }

    public SendMessage start(Long userId, Long chatId) {
        log.info("Command /start on execute by user tg id: {}, chat tg id: {}", userId, chatId);
        String msg = "Привет! Введи свое имя, рейнджер!";
        userService.createIfNotExist(
                new UserStore(userId),
                new ChatStore(chatId, ChatStateStore.WAITING_NAME));
        return createMessage(chatId, msg);
    }
}
