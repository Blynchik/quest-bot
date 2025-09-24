package dev.blynchik.quest_bot.config.bot.command;

import dev.blynchik.quest_bot.exception.handler.ExceptionHandler;
import dev.blynchik.quest_bot.model.chat.ChatStateStore;
import dev.blynchik.quest_bot.model.chat.ChatStore;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.service.model.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.createMessage;

@Component
@Slf4j
public class StartCommand extends BotCommand {
    private final UserService userService;
    private final ExceptionHandler exceptionHandler;


    @Autowired
    public StartCommand(UserService userService,
                        ExceptionHandler exceptionHandler) {
        super("/start", "");
        this.userService = userService;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("Command /start on execute by user tg id: {}, chat tg id: {}", user.getId(), chat.getId());
        String msg = "Привет! Введи свое имя, рейнджер!";
        try {
            userService.createIfNotExist(
                    new UserStore(user.getId()),
                    new ChatStore(chat.getId(), ChatStateStore.WAITING_NAME));
            absSender.execute(
                    createMessage(chat.getId(), msg));
        } catch (Exception e) {
            exceptionHandler.handle(absSender, chat.getId(), e);
        }
    }
}
