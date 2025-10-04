package dev.blynchik.quest_bot.config.bot.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

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

    public static SendMessage createMessageWithButton(Long chatId, String text, List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .build();
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, CallbackType callbackType) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackType.name())
                .build();
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, String callbackString) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackString)
                .build();
    }

    public static List<List<InlineKeyboardButton>> createInlineKeyboardButtonRows(List<InlineKeyboardButton> buttons) {
        return List.of(buttons);
    }

    public static List<List<InlineKeyboardButton>> createInlineKeyboardButtonRows(InlineKeyboardButton... buttons) {
        return List.of(
                Arrays.asList(buttons));
    }
}
