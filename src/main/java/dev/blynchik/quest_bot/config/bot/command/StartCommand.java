package dev.blynchik.quest_bot.config.bot.command;

import dev.blynchik.quest_bot.exception.handler.ExceptionHandler;
import dev.blynchik.quest_bot.service.message.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@Slf4j
public class StartCommand extends BotCommand {
    private final CommandHandler commandHandler;
    private final ExceptionHandler exceptionHandler;


    @Autowired
    public StartCommand(CommandHandler commandHandler,
                        ExceptionHandler exceptionHandler) {
        super("/start", "");
        this.commandHandler = commandHandler;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("Get command /start from user tg id: {}, chat tg id: {}", user.getId(), chat.getId());
        try {
            absSender.execute(
                    commandHandler.start(user.getId(), chat.getId()));
        } catch (Exception e) {
            exceptionHandler.handle(absSender, chat.getId(), e);
        }
    }
}
