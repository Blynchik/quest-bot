package dev.blynchik.quest_bot.config.bot.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@UtilityClass
@Slf4j
public class SendMessageUtil {
    public static SendMessage createMessage(Long chatId, String text) {
        log.info("Send message to tg chat id: {} with message: {}", chatId, text);
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode("Markdown")
                .disableWebPagePreview(true)
                .build();
    }
}
