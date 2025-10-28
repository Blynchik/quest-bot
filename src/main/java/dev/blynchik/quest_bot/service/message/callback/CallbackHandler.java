package dev.blynchik.quest_bot.service.message.callback;

import dev.blynchik.quest_bot.exception.exception.IllegalCallbackTypeException;
import dev.blynchik.quest_bot.exception.exception.IllegalConsequenceTypeException;
import dev.blynchik.quest_bot.exception.exception.NoPossibleResultException;
import dev.blynchik.quest_bot.exception.exception.UnexpectedCallbackException;
import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.ChangeNumStateParam;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.StartEventParam;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRule;
import dev.blynchik.quest_bot.model.content.result.ResultStore;
import dev.blynchik.quest_bot.model.user.PlayerCustom;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.service.message.command.CommandHandler;
import dev.blynchik.quest_bot.service.message.customizer.TextCustomizer;
import dev.blynchik.quest_bot.service.model.UserService;
import dev.blynchik.quest_bot.service.model.content.ActionService;
import dev.blynchik.quest_bot.service.model.content.EventService;
import dev.blynchik.quest_bot.service.model.content.ResultService;
import dev.blynchik.quest_bot.service.model.content.expression.ExpressionEvaluator;
import dev.blynchik.quest_bot.service.model.quest.PlayerCustomStateService;
import dev.blynchik.quest_bot.service.model.quest.QuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static dev.blynchik.quest_bot.config.bot.util.CallbackType.*;
import static dev.blynchik.quest_bot.config.bot.util.CallbackUtil.SEPARATOR;
import static dev.blynchik.quest_bot.config.bot.util.SendMessageUtil.*;

@Component
@Slf4j
public class CallbackHandler {
    private final UserService userService;
    private final QuestService questService;
    private final EventService eventService;
    private final ActionService actionService;
    private final ResultService resultService;
    private final ExpressionEvaluator evaluator;
    private final CommandHandler commandHandler;
    private final TextCustomizer textCustomizer;
    private final PlayerCustomStateService playerCustomStateService;

    @Autowired
    public CallbackHandler(UserService userService, QuestService questService, EventService eventService,
                           ActionService actionService, ResultService resultService, ExpressionEvaluator evaluator,
                           CommandHandler commandHandler, TextCustomizer textCustomizer,
                           PlayerCustomStateService playerCustomStateService) {
        this.userService = userService;
        this.questService = questService;
        this.eventService = eventService;
        this.actionService = actionService;
        this.resultService = resultService;
        this.evaluator = evaluator;
        this.commandHandler = commandHandler;
        this.textCustomizer = textCustomizer;
        this.playerCustomStateService = playerCustomStateService;
    }

    public SendMessage handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        log.info("Get callback: {} from chat tg id: {}", data, chatId);

        if (data.startsWith(RANDOM_QUEST.name())) {
            UserStore player = userService.getByChatId(chatId);
            QuestStore quest = questService.getRandom();
            PlayerCustom offerState = PlayerCustom.builder()
                    .ranger(player.getName())
                    .planet("Банана")
                    .system("Мергац")
                    .reward("10 000")
                    .tillDate(LocalDate.now().plusYears(1000L).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                    .rules(quest.getRule())
                    .ruleStates(playerCustomStateService.getStartState(player, quest))
                    .build();
            player = userService.prepareToOfferQuest(player, Map.of(quest.getId(), offerState));
            String messageText = textCustomizer.customizeQuestPreview(quest.getTitle(), quest.getDescr(), offerState);
            return createMessageWithButton(chatId, messageText,
                    createInlineKeyboardButtonRows(
                            createInlineKeyboardButton("Начать квест",
                                    "%s%s%s".formatted(START_QUEST.name(), SEPARATOR.replace(), quest.getId()))
                    ));
        }

        if (data.startsWith(START_QUEST.name())) {
            UserStore player = userService.getByChatId(chatId);
            String[] callbackData = data.split(SEPARATOR.replace());
            Long questId = Long.parseLong(callbackData[1]);
            EventStore event = questService.getFirstEventByQuestId(questId);
            List<ActionStore> actions = new ArrayList<>();
            for (ActionStore a : eventService.getActionsByEventId(event.getId())) {
                if (evaluator.evaluate(a.getCondition(), player)) actions.add(a);
                // здесь еще может быть проверка на actionStore.hideIfImprobable, но контексте данных квестов не нужна
            }
            player = userService.prepareToStartQuest(player, questId, event, actions);
            String messageText = textCustomizer.customizeEvent(
                    event.getDescr(),
                    actions.stream().map(ActionStore::getDescr).toList(),
                    player.getCustom(),
                    event.isHideState());
            List<InlineKeyboardButton> actionButtons = IntStream.range(0, actions.size())
                    .mapToObj(i -> createInlineKeyboardButton(String.valueOf(i + 1),
                            "%s%s%s".formatted(ACTION.name(), SEPARATOR.replace(), actions.get(i).getId())))
                    .toList();
            return createMessageWithButton(chatId, messageText,
                    createInlineKeyboardButtonRows(
                            actionButtons));
        }

        if (data.startsWith(ACTION.name())) {
            UserStore player = userService.getByChatId(chatId);
            Long actionId = Long.parseLong(
                    player.getExpectedCallback().stream()
                            .filter(ec -> ec.equals(data))
                            .findFirst()
                            .orElseThrow(
                                    () -> new UnexpectedCallbackException("Неверная команда."))
                            .split(SEPARATOR.replace())[1]);
            ResultStore result = null;
            List<ResultStore> results = actionService.getResultsByActionId(actionId);
            //здесь все таки лучше собрать список возможных результатов и выбрать какой-то по приоритету
            for (ResultStore r : results) {
                if (evaluator.evaluate(r.getCondition(), player)) {
                    result = r;
                    break;
                }
            }
            if (result == null) throw new NoPossibleResultException("Нет возможных результатов.");
            List<ConsequenceStore> consequences = resultService.getConsequencesByResultId(result.getId());
            Long nextEventId = null;
            for (ConsequenceStore con : consequences) {
                switch (con.getType()) {
                    case NEXT_EVENT -> nextEventId = ((StartEventParam) con.getParams()).getEventId();
                    case CHANGE_NUM_STATE -> {
                        var value = Integer.parseInt(player.getCustom().getRuleStates().get(((ChangeNumStateParam) con.getParams()).getKey())) + ((ChangeNumStateParam) con.getParams()).getValue();
                        player.getCustom().getRuleStates().put(((ChangeNumStateParam) con.getParams()).getKey(), String.valueOf(value));
                        if (value < ((NumRule) player.getCustom().getRules().get(((ChangeNumStateParam) con.getParams()).getKey())).getMin()) {
                            PlayerCustom custom = player.getCustom();
                            userService.prepareToFinishQuest(player);
                            return createMessageWithButton(chatId, ((NumRule) custom.getRules().get(((ChangeNumStateParam) con.getParams()).getKey())).getOutOfRuleDescr(),
                                    createInlineKeyboardButtonRows(
                                            createInlineKeyboardButton("Случайный квест", RANDOM_QUEST)
                                    ));
                        }
                    }
                    case FINISH_QUEST -> {
                        player = userService.prepareToFinishQuest(player);
                        return createMessageWithButton(chatId, "Рейнджер %s! Для вас есть новое задание!".formatted(player.getName()),
                                createInlineKeyboardButtonRows(
                                        createInlineKeyboardButton("Случайный квест", RANDOM_QUEST)
                                ));
                    }
                    default -> throw new IllegalConsequenceTypeException("Неверный тип последствия.");
                }
            }
            EventStore event = eventService.getById(nextEventId);
            List<ActionStore> actions = new ArrayList<>();
            for (ActionStore a : eventService.getActionsByEventId(event.getId())) {
                if (evaluator.evaluate(a.getCondition(), player)) actions.add(a);
                // здесь еще может быть проверка на actionStore.hideIfImprobable, но контексте данных квестов не нужна
            }
            player = userService.prepareToNextEvent(player, event, actions);
            String messageText = textCustomizer.customizeEvent(
                    event.getDescr(),
                    actions.stream().map(ActionStore::getDescr).toList(),
                    player.getCustom(),
                    event.isHideState());
            List<InlineKeyboardButton> actionButtons = IntStream.range(0, actions.size())
                    .mapToObj(i -> createInlineKeyboardButton(String.valueOf(i + 1),
                            "%s%s%s".formatted(ACTION.name(), SEPARATOR.replace(), actions.get(i).getId())))
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
