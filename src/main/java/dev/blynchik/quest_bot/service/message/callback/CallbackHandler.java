package dev.blynchik.quest_bot.service.message.callback;

import dev.blynchik.quest_bot.exception.exception.IllegalCallbackTypeException;
import dev.blynchik.quest_bot.exception.exception.IllegalConsequenceTypeException;
import dev.blynchik.quest_bot.exception.exception.NoPossibleResultException;
import dev.blynchik.quest_bot.exception.exception.UnexpectedCallbackException;
import dev.blynchik.quest_bot.model.chat.ChatStateStore;
import dev.blynchik.quest_bot.model.chat.ChatStore;
import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.model.content.consequence.params.StartEventParam;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import dev.blynchik.quest_bot.model.content.result.ResultStore;
import dev.blynchik.quest_bot.model.player.PlayerCustom;
import dev.blynchik.quest_bot.model.player.PlayerStore;
import dev.blynchik.quest_bot.service.message.command.CommandHandler;
import dev.blynchik.quest_bot.service.message.customizer.TextCustomizer;
import dev.blynchik.quest_bot.service.model.ChatService;
import dev.blynchik.quest_bot.service.model.PlayerService;
import dev.blynchik.quest_bot.service.model.content.ActionService;
import dev.blynchik.quest_bot.service.model.content.EventService;
import dev.blynchik.quest_bot.service.model.content.QuestService;
import dev.blynchik.quest_bot.service.model.content.ResultService;
import dev.blynchik.quest_bot.service.model.content.expression.ExpressionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static dev.blynchik.quest_bot.config.bot.util.CallbackType.*;
import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.*;
import static dev.blynchik.quest_bot.model.chat.ChatStateStore.ON_QUEST;

@Component
@Slf4j
public class CallbackHandler {
    private final QuestService questService;
    private final PlayerService playerService;
    private final ChatService chatService;
    private final EventService eventService;
    private final ActionService actionService;
    private final ResultService resultService;
    private final ExpressionEvaluator evaluator;
    private final CommandHandler commandHandler;
    private final TextCustomizer textCustomizer;
    private final String SEPARATOR = "::";

    @Autowired
    public CallbackHandler(QuestService questService, PlayerService playerService, ChatService chatService,
                           EventService eventService, ActionService actionService, ResultService resultService,
                           ExpressionEvaluator evaluator, CommandHandler commandHandler, TextCustomizer textCustomizer) {
        this.questService = questService;
        this.playerService = playerService;
        this.chatService = chatService;
        this.eventService = eventService;
        this.actionService = actionService;
        this.resultService = resultService;
        this.evaluator = evaluator;
        this.commandHandler = commandHandler;
        this.textCustomizer = textCustomizer;
    }

    public SendMessage handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        log.info("Get callback: {} from chat tg id: {}", data, chatId);

        if (data.startsWith(RANDOM_QUEST.name())) {
            QuestStore quest = questService.getRandom();
            PlayerStore player = chatService.getPlayerByChatId(chatId);
            PlayerCustom custom = new PlayerCustom(player.getName(), "Банана", "Мергац", "10 000", LocalDate.now().plusYears(1000L).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            playerService.updateOffer(player, Map.of(quest.getId(), custom));
            String messageText = textCustomizer.customizeQuestPreview(quest.getTitle(), quest.getDescr(), custom);
            return createMessageWithButton(chatId, messageText,
                    createInlineKeyboardButtonRows(
                            createInlineKeyboardButton("Начать квест", START_QUEST.name() + SEPARATOR + quest.getId())
                    ));
        }

        if (data.startsWith(START_QUEST.name())) {
            String[] callbackData = data.split(SEPARATOR);
            Long questId = Long.parseLong(callbackData[1]);
            PlayerStore player = chatService.getPlayerByChatId(chatId);
            EventStore event = questService.getFirstEventByQuestId(questId);
            List<ActionStore> actions = eventService.getActionsByEventId(event.getId()).stream()
                    .filter(a -> evaluator.evaluate(a.getCondition()))
                    // здесь еще может быть проверка на actionStore.hideIfImprobable, но контексте данных квестов не нужна
                    .toList();
            PlayerCustom custom = player.getOffer().get(questId);
            playerService.updateEvent(player, event);
            playerService.updateCustom(player, custom);
            playerService.clearOffer(player);
            ChatStore chat = chatService.getByTgId(chatId);
            chatService.updateChatState(chat, ON_QUEST);
            playerService.updateExpectedCallback(player,
                    actions.stream()
                            .map(a -> ACTION.name() + SEPARATOR + a.getId())
                            .toList());
            String messageText = textCustomizer.customizeEvent(
                    event.getDescr(),
                    actions.stream().map(ActionStore::getDescr).toList(),
                    custom);
            List<InlineKeyboardButton> actionButtons = IntStream.range(0, actions.size())
                    .mapToObj(i -> createInlineKeyboardButton(String.valueOf(i + 1), ACTION.name() + SEPARATOR + actions.get(i).getId()))
                    .toList();
            return createMessageWithButton(chatId, messageText,
                    createInlineKeyboardButtonRows(
                            actionButtons));
        }

        if (data.startsWith(ACTION.name())) {
            PlayerStore player = chatService.getPlayerByChatId(chatId);
            Long actionId = Long.parseLong(
                    player.getExpectedCallback().stream()
                            .filter(ec -> ec.equals(data))
                            .findFirst()
                            .orElseThrow(
                                    () -> new UnexpectedCallbackException("Неверная команда."))
                            .split(SEPARATOR)[1]);
            ResultStore result = actionService.getResultsByActionId(actionId).stream()
                    .filter(r -> evaluator.evaluate(r.getCondition()))
                    // здесь должно быть решение о том какой результат выдать, если несколько подходят по условиям
                    .findFirst()
                    .orElseThrow(
                            () -> new NoPossibleResultException("Нет возможных результатов."));
            List<ConsequenceStore> consequences = resultService.getConsequencesByResultId(result.getId());
            Long nextEventId = null;
            for (ConsequenceStore con : consequences) {
                switch (con.getType()) {
                    case START_EVENT -> nextEventId = ((StartEventParam) con.getParams()).getEventId();
                    case FINISH_EVENT -> {
                        playerService.finishQuest(player);
                        chatService.updateChatState(chatService.getByTgId(chatId), ChatStateStore.WAITING_QUEST);
                        return createMessageWithButton(chatId, "Рейнджер %s! Для вас есть новое задание!".formatted(player.getName()),
                                createInlineKeyboardButtonRows(
                                        createInlineKeyboardButton("Случайный квест", RANDOM_QUEST)
                                ));
                    }
                    default -> throw new IllegalConsequenceTypeException("Неверный тип последствия.");
                }
            }
            EventStore event = eventService.getById(nextEventId);
            playerService.updateEvent(player, event);
            List<ActionStore> actions = eventService.getActionsByEventId(event.getId()).stream()
                    .filter(a -> evaluator.evaluate(a.getCondition()))
                    // здесь еще может быть проверка на actionStore.hideIfImprobable, но контексте данных квестов не нужна
                    .toList();

            playerService.updateExpectedCallback(player,
                    actions.stream()
                            .map(a -> ACTION.name() + SEPARATOR + a.getId())
                            .toList());
            String messageText = textCustomizer.customizeEvent(
                    event.getDescr(),
                    actions.stream().map(ActionStore::getDescr).toList(),
                    player.getCustom());
            List<InlineKeyboardButton> actionButtons = IntStream.range(0, actions.size())
                    .mapToObj(i -> createInlineKeyboardButton(String.valueOf(i + 1), ACTION.name() + SEPARATOR + actions.get(i).getId()))
                    .toList();

            return createMessageWithButton(chatId, messageText,
                    createInlineKeyboardButtonRows(
                            actionButtons));
        }

        if (data.startsWith(START.name())) {
            return commandHandler.start(callbackQuery.getFrom().getId(), chatId);
        }

        throw new IllegalCallbackTypeException("Неизвестная команда.");
    }
}
