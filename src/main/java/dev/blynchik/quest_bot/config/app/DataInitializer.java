package dev.blynchik.quest_bot.config.app;

import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.ChangeNumStateParam;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.StartEventParam;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.content.expression.BinaryLogicOperator;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionStore;
import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.AlwaysParams;
import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.num.ComparisonType;
import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.num.UserNumParams;
import dev.blynchik.quest_bot.model.content.expression.impl.NodeExpression;
import dev.blynchik.quest_bot.model.content.expression.impl.SingleExpression;
import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import dev.blynchik.quest_bot.model.content.quest.rule.Rule;
import dev.blynchik.quest_bot.model.content.quest.rule.impl.NumRuleWithDescr;
import dev.blynchik.quest_bot.model.content.result.ResultStore;
import dev.blynchik.quest_bot.service.model.content.ConditionService;
import dev.blynchik.quest_bot.service.model.content.EventService;
import dev.blynchik.quest_bot.service.model.quest.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements ApplicationRunner {
    private final QuestService questService;
    private final ConditionService conditionService;
    private final EventService eventService;

    @Autowired
    public DataInitializer(QuestService questService,
                           ConditionService conditionService,
                           EventService eventService) {
        this.questService = questService;
        this.conditionService = conditionService;
        this.eventService = eventService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConditionStore alwaysTrueCondition = conditionService.create(new ConditionStore());
        ConditionStore alwaysFalseCondition = conditionService.create(new ConditionStore(new AlwaysParams(false)));
        ConditionStore num40Condition = conditionService.create(new ConditionStore(new UserNumParams(ComparisonType.MORE_EQ, 40, "Физическое состояние")));
        ConditionStore num80Condition = conditionService.create(new ConditionStore(new UserNumParams(ComparisonType.MORE_EQ, 80, "Координация")));
        ConditionStore num80Condition2 = conditionService.create(new ConditionStore(new UserNumParams(ComparisonType.MORE_EQ, 80, "Психологическое состояние")));
        ConditionStore num60Condition2 = conditionService.create(new ConditionStore(new UserNumParams(ComparisonType.MORE_EQ, 60, "Психологическое состояние")));
        ConditionStore num70Condition = conditionService.create(new ConditionStore(new UserNumParams(ComparisonType.MORE_EQ, 70, "Координация")));
        ConditionStore num69Condition = conditionService.create(new ConditionStore(new UserNumParams(ComparisonType.LESS_EQ, 69, "Координация")));

        Map<String, Rule> rule = new HashMap<>();
        rule.put("Физическое состояние", new NumRuleWithDescr("Физическое состояние", 100, 0, 100,
                Map.of(
                        0, "Вы на грани смерти от истощения",
                        22, "Вы чувствуете, что смертельно устали",
                        44, "Ваше тело ломит и крутит",
                        66, "Усталость дает о себе знать",
                        90, "Ваша физическая форма идеальна"
                ),
                "Внезапно вы стали ощущать, что силы вас неизбежно покидают. Ноги ослабли, вы упали и поняли, что больше не сможете двигаться. Вас удалось привести в порядок с большим трудом. Извинившись за сорванный эксперимент, на еще дрожащих ногах вы покинули лабораторию."));
        rule.put("Психологическое состояние", new NumRuleWithDescr("Психологическое состояние", 100, 0, 100,
                Map.of(
                        0, "Ваша воля окончательно сломлена",
                        11, "Вам с трудом удается сохранять рассудок",
                        40, "Вы начинаете паниковать",
                        60, "Вам становится страшно",
                        90, "Вы спокойны и собраны, как олимпиец"
                ),
                "У вас совершенно помутился рассудок, вы стали действовать неадекватно, набрасываться на стены и махать руками. Затем вы упали на пол и принялись дергаться, как эпилептик. Вас с большим трудом удалось привести в чувство. Извинившись за сорванный эксперимент, вы покинули лабораторию."));
        rule.put("Работа сердца", new NumRuleWithDescr("Работа сердца", 100, 0, 100,
                Map.of(
                        0, "Кажется, у вас инфаркт",
                        11, "Ужасно щемит в груди",
                        40, "Начинается явная аритмия сердца",
                        60, "Работа сердца удовлетворительна",
                        90, "Ваше сердце бьется, как часы"
                ),
                "Внезапно вы почувствовали нестерпимую боль в груди; это продолжалось каких-то несколько секунд, после чего вы упали замертво. Страшный, ужасный инфаркт, вывернувший ваше сердце наизнанку."));
        rule.put("Координация", new NumRuleWithDescr("Координация", 100, 0, 100,
                Map.of(
                        0, "Ваше тело вам практически не подчиняется",
                        11, "Вы почти не ощущаете свои конечности",
                        40, "Вам становится трудно контролировать свое тело",
                        60, "Координация дает сбои",
                        90, "Ваши действия идеально точны"
                ),
                "Внезапно мир перевернулся с ног на голову. Ваши собственные конечности перестали вас слушаться, движения стали резкими и размашистыми, вы промахивались мимо дверных косяков и натыкались на мебель, словно пьяный. Попытка сделать шаг обернулась жалким падением, после которого вы, словно перевернутый жук, могли лишь беспомощно дрыгать руками и ногами, не в силах подняться. Вас с большим трудом удалось привести в порядок и поставить на ноги. Извинившись за сорванный эксперимент, вы, цепляясь за стены и спотыкаясь на ровном месте, покинули лабораторию."));
        rule.put("Самочувствие", new NumRuleWithDescr("Самочувствие", 100, 0, 100,
                Map.of(
                        0, "Вам так плохо, что лучше умереть",
                        11, "Вам очень плохо",
                        40, "Вам плохо",
                        60, "Самочувствие так себе",
                        90, "Вы прекрасно чувствуете себя"
                ),
                "У вас уже давно кружилась голова, жгло в области живота и сильно щемило в груди. Наконец болевой шок дал о себе знать, и вы потеряли сознание. Вы пробыли без сознания в течение часа, и вас с большим трудом удалось откачать. Извинившись за сорванный эксперимент, на еще дрожащих ногах вы покинули лабораторию."
        ));

        QuestStore quest = questService.create(
                QuestStore.builder()
                        .title("Страшная смерть")
                        .descr("""
                                Послушайте, {ranger_name}, окажите нам услугу. На планете {planet_name}, в системе {system_name}, мы проводим эксперимент по изучению воздействия разных химических веществ на человеческий организм. Все, что нам нужно, это здоровый человек для эксперимента. Мы платим огромные деньги - {reward_value} cr! Не упустите такую возможность. Но только получить эти деньги вы сможете, если эксперимент будет проведен до {date}, не иначе.
                                """)
                        .rule(rule)
                        .build());

        EventStore eventStoreL30 = eventService.create(
                EventStore.builder()
                        .descr("Подъемник тронулся вверх, но в этот момент вы почувствовали такую боль в груди и такое сумасшедшее глазное давление, что начали понимать, что теряете рассудок. В панике, с широко раскрытыми глазами вы ухватились за поручни подъемника и стали кричать, но изо рта выходили лишь пузыри. Подъемник вез вас вверх, вы чувствовали, что больше не выдерживаете, но старались держаться изо всех сил.\nДостигнув поверхности, вы увидели престранную картину. Здание было окружено гаальскими силами безопасности. Только вчера правительство получило информацию, что на базе одного из гаальских научных центров действовала подпольная пеленгская лаборатория. Ее оборудовали шесть пеленгов, которые официально числились в штате гаальского центра, но фактически были шпионами, которые пользовались своей аккредитацией, чтобы получать доступ к гаальскому оборудованию. При помощи этого оборудования, а также нескольких тонн похищенного вещества, и была организована лаборатория, где пеленги намеревались провести эксперименты по воздействию калольных и кислокалольных сред на гаальский, фэянский и человеческий организмы. По результатам этих опытов пеленги планировали создать новые виды химического оружия на основе калольной кислоты, которая вызывает у гуманоидов кардиогенарный эффект, или эффект сердечного коллапса. Планировалось исследовать поведение пехоты в условиях воздействия кислоты и изучить на практике принцип кардиогенарного удара.\nНо этим планам не удалось осуществиться. Полиция ворвалась в помещение и шесть пеленгов, а также купленный ими гаальский сотрудник, были арестованы. Вы были препровождены в клинику, где провели остаток дня, приходя в норму. Гаальское правительство выразило вам официальное соболезнование по поводу инцидента. Когда вы заявили, что подписали контракт с пеленгской стороной, гаальцы заверили вас, что окажут на пеленгов давление, и вы непременно получите свои деньги.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Завершить задание")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.FINISH_QUEST)
                                                                        .params(new StartEventParam())
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(false)
                        .build());

        EventStore eventStoreP61 = eventService.create(
                EventStore.builder()
                        .descr("Вы пытаетесь нажать на кнопку, но руки дрожат, и вы не в силах даже попасть пальцем в кнопку. Превозмогая невыносимую боль в груди, усугубляемую нервозным состоянием и жжением во рту, вы собираете остатки сил, сосредоточиваетесь и нажимаете на кнопку.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL30.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP60 = eventService.create(
                EventStore.builder()
                        .descr("Дрожащими руками вам удалось нажать кнопку.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL30.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL38 = eventService.create(
                EventStore.builder()
                        .descr("Через боль и невероятное жжение в области живота, непослушными ногами, вы преодолеваете несколько метров и, потеряв последние силы, опускаетесь на пол подъемного механизма.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Нажать кнопку \"Вверх\"")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(
                                                        ResultStore.builder()
                                                                .condition(new SingleExpression(num70Condition.getId()))
                                                                .consequences(List.of(
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.NEXT_EVENT)
                                                                                .params(new StartEventParam(eventStoreP60.getId()))
                                                                                .build(),
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                                .params(new ChangeNumStateParam("Физическое состояние", -10))
                                                                                .build(),
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                                .params(new ChangeNumStateParam("Психологическое состояние", 5))
                                                                                .build(),
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                                .params(new ChangeNumStateParam("Работа сердца", -6))
                                                                                .build(),
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                                .params(new ChangeNumStateParam("Самочувствие", -10))
                                                                                .build()
                                                                ))
                                                                .build(),
                                                        ResultStore.builder()
                                                                .condition(new SingleExpression(num69Condition.getId()))
                                                                .consequences(List.of(
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.NEXT_EVENT)
                                                                                .params(new StartEventParam(eventStoreP61.getId()))
                                                                                .build(),
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                                .params(new ChangeNumStateParam("Физическое состояние", -15))
                                                                                .build(),
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                                .params(new ChangeNumStateParam("Работа сердца", -9))
                                                                                .build(),
                                                                        ConsequenceStore.builder()
                                                                                .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                                .params(new ChangeNumStateParam("Самочувствие", -11))
                                                                                .build()))
                                                                .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL36 = eventService.create(
                EventStore.builder()
                        .descr("Вы с трудом поднимаетесь на ноги, падаете, поднимаетесь опять и, преодолевая все возрастающую боль в груди, покачиваясь, направляетесь в сторону подъемника.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Зайти в подъемник")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL38.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -8))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", 1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -9))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -6))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -10))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL28 = eventService.create(
                EventStore.builder()
                        .descr("Здание было окружено гаальскими силами безопасности. Только вчера правительство получило информацию, что на базе одного из гаальских научных центров действовала подпольная пеленгская лаборатория. Ее оборудовали шесть пеленгов, которые официально числились в штате гаальского центра, но фактически были шпионами, которые пользовались своей аккредитацией, чтобы получать доступ к гаальскому оборудованию. При помощи этого оборудования, а также нескольких тонн похищенного вещества, и была организована лаборатория, где пеленги намеревались провести эксперименты по воздействию калольных и кислокалольных сред на гаальский, фэянский и человеческий организмы. По результатам этих опытов пеленги планировали создать новые виды химического оружия на основе калольной кислоты, которая вызывает у гуманоидов кардиогенарный эффект, или эффект сердечного коллапса. Планировалось исследовать поведение пехоты в условиях воздействия кислоты и изучить на практике принцип кардиогенарного удара.\nНо этим планам не удалось осуществиться. Полиция ворвалась в помещение и шесть пеленгов, а также купленный ими гаальский сотрудник, были арестованы. По словам пеленгов, бассейн был пуст, и высокотоксичную массу стали спускать. Но через полчаса один из гаальских служащих, что случайно зашел в помещение, сообщил, что на дне бассейна находятся человеческие останки, насквозь прожженные калольной кислотой.\nПеленгские ученые-шпионы были приговорены к высшим мерам наказания.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Завершить задание")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.FINISH_QUEST)
                                                                        .params(new StartEventParam())
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(false)
                        .build());

        EventStore eventStoreL26 = eventService.create(
                EventStore.builder()
                        .descr("Наконец вы встаете и принимаете волевое решение - встать и идти к подъемнику.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Идти к подъемнику")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL36.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -7))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", 2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -9))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -6))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL27 = eventService.create(
                EventStore.builder()
                        .descr("Вы стали чувствовать, что теряете сознание. Рассудок мутнел, силы безвозвратно уходили, равно как и вера, что вас когда-нибудь вытащат. Перед глазами проплывали картинки детства, юности. Хватит ли вам моральной силы принять решение?")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Встать!")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL26.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Закрыть глаза и спокойно умереть")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL28.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL25 = eventService.create(
                EventStore.builder()
                        .descr("Оператор молчал, и калольное нагнетание не кончалось, а только усиливалось. Ощущение, что о вас попросту забыли, все возрастало. Однако ваш рассудок был настолько подорван, что принять какое-либо решение было крайне трудно. Вы лежали и апатично смотрели вверх.\n\nидти к подъемнику...\nидти к подъемнику...\n\nэта мысль пробивалась сквозь толщу гипнотического оцепенения, которое сковывало всю вашу психику.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Встать!")
                                        .condition(new SingleExpression(num60Condition2.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL26.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Продолжать лежать и ждать сигнала")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL27.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -11))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -20))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -20))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL24 = eventService.create(
                EventStore.builder()
                        .descr("Вы лежали еще несколько минут, пытаясь понять, когда все это кончится и вам можно будет благополучно подняться на поверхность. Но очевидно, происходило что-то неладное. Впервые вам пришла эта мысль, когда вы услышали потрескивания и постукивания в наушнике. Но вам было очень трудно вообще о чем-либо думать, казалось намного проще просто лежать и ждать. Вас охватил почти гипнотический транс, воля ослабла, и ни о чем не хотелось размышлять. Кардиогенарный удар - что это такое?")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Встать!")
                                        .condition(new SingleExpression(num80Condition2.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL26.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -8))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Продолжать лежать и ждать сигнала")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL25.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -7))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL41 = eventService.create(
                EventStore.builder()
                        .descr("Пошатываясь и еле держась на ногах, вы направились к подъемнику. Вам еле удалось нажать на нужную кнопку, подъемник тронулся, и вы стали подниматься.\nНо, оказавшись наверху, вместо носилок и медперсонала вы обнаружили полнейшую суматоху. Гришхилл, еще трое пеленгов и один дрожащий гаалец со смертельно испуганным видом бегали по лаборатории, перенося вещи. Будка оператора была пуста!\nЗавидев вас, Гришхилл бросился в кабинет и через две секунды выскочил оттуда с вашей одеждой в руках.\n- {ranger_name}, хорошо, что вы здесь. Скорее берите свои шмотки и уходите через запасной выход. Быстрее, прошу вас!\n- Но что случилось? Как же эксперимент?\n- К черту эксперимент! Нас засекла враждебная группа - гаальские мафиози. Они уже давно за нами охотились и наконец засекли. Делай ноги, если не хочешь остаться тут лежать навсегда!\nВы все явственней различали голоса гаальцев за дверями и характерное шипение галогенного ножа, которым вскрывают двери и стены.\n- А как же мои деньги? - спросили вы, наспех одеваясь.\n- Какие деньги?! Скажи спасибо, что жив остался! Быстрее, запасной выход там за трубой. Мы его как раз готовили на такой случай.\nС этими словами Гришхилл бросился прочь, таскать какие-то вещи. Двое пеленгов уже ждали его перед выходом, нетерпеливо переминаясь с ноги на ногу. Вы, недолго думая, бросились туда, открыли узкий люк и вылезли наружу. Вам все еще было плохо, в голове мутило, а сердце билось учащенно, но благодаря инстинкту самосохранения вам удалось быстро покинуть это место. Убегая, вы заметили вокруг здания машины гаальских служб безопасности, но думать вам сейчас хотелось меньше всего. Хорошо, что вас никто не задержал и не убил. Действительно, к черту деньги. Жизнь - вот что ценнее всего на свете...")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Завершить задание")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.FINISH_QUEST)
                                                                        .params(new StartEventParam())
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(false)
                        .build());

        EventStore eventStoreP53 = eventService.create(
                EventStore.builder()
                        .descr("Вы легли навзничь и стали смотреть вверх. Жидкость вокруг вас пульсировала слегка неритмичными колебаниями, грудь сжимало все больше и больше, становилось трудно дышать, хотя вы старательно хватали ртом воздух из трубки. Как вы ни старались выровнять пульс медитативными упражнениями, ощущение, что сердце выпрыгивает из груди, лишь нарастало.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL24.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL23 = eventService.create(
                EventStore.builder()
                        .descr("ТЕСТ №5, ЗАКЛЮЧИТЕЛЬНЫЙ: ИСПЫТАНИЕ КАРДИОГЕНАРНОГО ЭФФЕКТА.\nИСПЫТУЕМЫЙ, ЛОЖИТЕСЬ НА ДНО И ЖДИТЕ ИНСТРУКЦИЙ.\n\nБассейн задрожал, жидкость стала ходить ходуном. Что это, цунами? Вы почувствовали, как стало сжимать в груди. Силы стали вас покидать, голова заболела еще сильнее, и ноги перестали держать тело. Неужели это конец?")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Идти к подъемнику")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL41.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -6))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -9))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -17))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -6))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -12))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Лечь")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP53.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP51 = eventService.create(
                EventStore.builder()
                        .descr("Да, с этим громилой вам точно не справится, думаете вы и продолжаете отступление. Все-таки зря вы так пренебрегали физической подготовкой всю свою жизнь. Гарвард, Гарвард... и зачем он вам теперь, этот Гарвард?\nНо вдруг макет дернулся, развернулся и укатил туда же, откуда выкатил. Видимо, струсил. Или кончился раунд. В любом случае, вы неплохо держались, и можно себя поздравить.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL23.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -6))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -15))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP50 = eventService.create(
                EventStore.builder()
                        .descr("Вы стукнули пеленга, как заправский боксер. Он чуть подпрыгнул и ответил вам тем же. Не больно, но и не приятно.\nНо вдруг он дернулся, развернулся и укатил туда же, откуда выкатил. Видимо, струсил. Или кончился раунд. В любом случае, вы неплохо держались, и можно себя поздравить.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL23.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -6))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -15))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP49 = eventService.create(
                EventStore.builder()
                        .descr("Эх, была не была, подумали вы и попытались издать воинственный клич. Вспомнив, что вы в жидкой среде и дышите в маске, вы отказались от этой идеи и набросились на макет в полном молчании.\nВы катались по дну бассейна и колошматили друг друга кулаками. В конце концов, пеленг сдал, произвел несколько неопределенных движений и укатил в свое отверстие в стене. Это была победа, которая придавала стимул к новым свершениям.\nНо как же вам дурно... все болит и ломит. В чем плюс, так это в том, что вы абсолютно овладели своими движениями в этой неестественной среде.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL23.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -6))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -15))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL14 = eventService.create(
                EventStore.builder()
                        .descr("Пеленг угрожающе наступает, размахивая ручищами. Напрасно, напрасно вы не занимались в молодости каратэ!\nИнтересно, сколько же еще надо мочить этого гада. Неужели до победного?")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Продолжать отступать")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP51.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -8))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 1))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Провести правый хук по почкам")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP50.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -4))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Наброситься всем телом, завалить на пол и мочить, пока оператор не остановит")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP49.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -14))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", 10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -10))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP48 = eventService.create(
                EventStore.builder()
                        .descr("Страх сковал вас, от ужаса под маской выступил пот. Вы продолжаете отступать. Зато немного отдохнули и привели в порядок пульс.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL14.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP47 = eventService.create(
                EventStore.builder()
                        .descr("Замечательный прием - пеленг валится на дно.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL14.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP28 = eventService.create(
                EventStore.builder()
                        .descr("Отлично сработано - пеленг отлетает, а точнее отплывает, к стенке бассейна.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL14.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL13 = eventService.create(
                EventStore.builder()
                        .descr("Пеленг надвигается с весьма грозным видом. Что делать?")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Слишком страшно, не знаю, что делать - продолжать отступать")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP48.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -12))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 3))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Нагнуться и сделать подсечку")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP47.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Подпрыгнуть и ударить обеими ногами в живот")
                                        .condition(new NodeExpression(new SingleExpression(num40Condition.getId()), BinaryLogicOperator.AND, new SingleExpression(num80Condition.getId())))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP28.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -8))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -4))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP27 = eventService.create(
                EventStore.builder()
                        .descr("Вы провели удар справа по челюсти, на что пеленг ответил вам тем же. В глазах у вас помутнело, и вы едва не упали. Но, кажется, теперь вам стало ясней, как с ним драться - похоже, он бьет только на уровне своей головы. Видимо, так шарниры устроены.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL13.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP26 = eventService.create(
                EventStore.builder()
                        .descr("Вы приняли оборонительную позицию и избрали тактику позиционного отступления. Пеленг просто ужасен! Неужели вам придется с ним драться!")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL13.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL22 = eventService.create(
                EventStore.builder()
                        .descr("ТЕСТ №4: РУКОПАШНЫЙ БОЙ В КИСЛОКАЛОЛЬНОЙ СРЕДЕ.\nИСПЫТУЕМЫЙ, АТАКУЙТЕ УСЛОВНОГО ПРОТИВНИКА.\n\nВ стене открывается отверстие и из него выезжает модель пеленга в плавках. Она надвигается на вас и размахивает руками, всем видом давая понять, что намерения у нее далеки от дружественных.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Стукнуть по голове и быстро отступить")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP27.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -10))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Отходить и защищаться")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP26.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -1))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP80p2 = eventService.create(
                EventStore.builder()
                        .descr("Вы стоите, пытаясь расслабиться и привести в норму пульс.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP46p2 = eventService.create(
                EventStore.builder()
                        .descr("Вы несколько раз бросили палку в неподвижную мишень. Только один раз вам удалось попасть, но, кажется, вы начали свыкаться с ощущением ватных рук. Однако вы немного устали, пульс участился, а голова продолжала болеть.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP79p2 = eventService.create(
                EventStore.builder()
                        .descr("Вы лежите, пытаясь расслабиться и привести в норму пульс.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP45p2 = eventService.create(
                EventStore.builder()
                        .descr("Вы несколько раз бросили палку в неподвижную мишень. Только один раз вам удалось попасть, но, кажется, вы начали свыкаться с ощущением ватных рук. Однако вы немного устали, пульс участился, а голова продолжала болеть.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL21p2 = eventService.create(
                EventStore.builder()
                        .descr("Вы стали делать круги по дну бассейна, водя руками вверх и вниз. Головная боль усилилась, стало трудно двигаться, конечности стали словно ватными. Вы попробовали привыкнуть к этому состоянию, но это оказалось не так-то легко.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Спокойно стоять")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP80p2.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 1))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Взять палку и попытаться попасть ей в мишень, таким образом привыкая к ватным рукам")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP46p2.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL20p2 = eventService.create(
                EventStore.builder()
                        .descr("Вы полежали некоторое время, стараясь производить как можно меньше движений. Головная боль усиливалась, и вы почувствовали, что стало трудней двигать конечностями, словно они ватные.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Ходить кругами, стараясь сосредоточиться")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL21p2.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Спокойно лежать")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP79p2.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 1))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Взять палку и попытаться попасть ей в мишень, таким образом привыкая к ватным рукам")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP45p2.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP80 = eventService.create(
                EventStore.builder()
                        .descr("Вы стоите, пытаясь расслабиться и привести в норму пульс.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP46 = eventService.create(
                EventStore.builder()
                        .descr("Вы несколько раз бросили палку в неподвижную мишень. Только один раз вам удалось попасть, но, кажется, вы начали свыкаться с ощущением ватных рук. Однако вы немного устали, пульс участился, а голова продолжала болеть.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP79 = eventService.create(
                EventStore.builder()
                        .descr("Вы лежите, пытаясь расслабиться и привести в норму пульс.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP45 = eventService.create(
                EventStore.builder()
                        .descr("Вы несколько раз бросили палку в неподвижную мишень. Только один раз вам удалось попасть, но, кажется, вы начали свыкаться с ощущением ватных рук. Однако вы немного устали, пульс участился, а голова продолжала болеть.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL21 = eventService.create(
                EventStore.builder()
                        .descr("Вы стали делать круги по дну бассейна, водя руками вверх и вниз. Головная боль усилилась, стало трудно двигаться, конечности стали словно ватными. Вы попробовали привыкнуть к этому состоянию, но это оказалось не так-то легко.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Лечь на пол и закрыть глаза")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL20p2.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Спокойно стоять")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP80.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 1))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Взять палку и попытаться попасть ей в мишень, таким образом привыкая к ватным рукам")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP46.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL20 = eventService.create(
                EventStore.builder()
                        .descr("Вы полежали некоторое время, стараясь производить как можно меньше движений. Головная боль усиливалась, и вы почувствовали, что стало трудней двигать конечностями, словно они ватные.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Ходить кругами, стараясь сосредоточиться")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL21.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Спокойно лежать")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP79.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 1))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Взять палку и попытаться попасть ей в мишень, таким образом привыкая к ватным рукам")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP45.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL17 = eventService.create(
                EventStore.builder()
                        .descr("ТЕСТ №3: ПОВЫШЕНИЕ КОНЦЕНТРАЦИИ КАЛОЛЬНОЙ КИСЛОТЫ.\nИСПЫТУЕМЫЙ, ДЕЙСТВУЙТЕ ПО СОБСТВЕННОМУ УСМОТРЕНИЮ.\n\nИх отверстий в стене стала поступать темная жидкость, и пространство вокруг вас стало сгущаться. Вы ощутили на коже легкое покалывание, а во рту едва различимый привкус, напоминающий то ли серу, то ли мел. Слегка закружилась голова.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Лечь на пол и закрыть глаза")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL20.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Ходить кругами, стараясь сосредоточиться")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL21.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());


        EventStore eventStoreP34 = eventService.create(
                EventStore.builder()
                        .descr("Вы целитесь со значительным упреждением, и палка точно поражает мишень. Ну просто Робин Гуд!")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL17.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -8))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP33 = eventService.create(
                EventStore.builder()
                        .descr("Вы целитесь с небольшим упреждением и бросаете, но мишень успевает уйти вперед, и палка попадает за мишенью. Ну что вы за мазила! Вы чувствуете, как у вас начинают дрожать руки, и вы не можете как следует сосредоточиться.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL17.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -8))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP32 = eventService.create(
                EventStore.builder()
                        .descr("Вы бросаете палку, но она попадает далеко позади мишени. Вас охватывает злость. Вы чувствуете, как у вас начинают дрожать руки и вы не можете как следует сосредоточиться.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL17.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -8))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL16 = eventService.create(
                EventStore.builder()
                        .descr("Теперь мишень начинает двигаться быстрее.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Прицелиться подальше перед мишенью и бросить")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP34.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Прицелиться слегка перед мишенью и бросить")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP33.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -7))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Прицелиться точно в мишень и бросить")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP32.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -7))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP31 = eventService.create(
                EventStore.builder()
                        .descr("Вы целитесь с небольшим упреждением и промахиваетесь - цель успевает уйти вперед. Какая неудача! Этот сироп совершенно не приспособлен для таких упражнений.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL16.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP30 = eventService.create(
                EventStore.builder()
                        .descr("Вы целитесь с небольшим упреждением и бросаете - точно в цель! Замечательно.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL16.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP29 = eventService.create(
                EventStore.builder()
                        .descr("Вы бросаете палку, но она попадает за мишенью. Неудача!")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL16.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL15 = eventService.create(
                EventStore.builder()
                        .descr("ТЕСТ №2: ЗАМЕРЫ ГЛАЗОМЕРА И НЕЙРОННОЙ АКТИВНОСТИ.\nИСПЫТУЕМЫЙ, ВОЗЬМИТЕ ОБЪЕКТ.\n\nВ стене открылось отверстие, и из него выехала тележка, на которой лежала пластиковая палка длиной примерно в метр. Недоумевая, вы взяли ее в руку.\n\nИСПЫТУЕМЫЙ, ОТОЙДИТЕ НА РАССТОЯНИЕ ПЯТИ МЕТРОВ И БРОСАЙТЕ ПАЛКУ В ДВИЖУЩУЮСЯ МИШЕНЬ НА СТЕНЕ.\n\nТут вы заметили, что на той стене, где было отверстие, действительно находилась небольшая круглая панель диаметром примерно в полметра. Эта панель была неподвижной, но теперь начала медленно, примерно со скоростью неторопливого шага, перемещаться вдоль стены - вправо, влево.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Прицелиться подальше перед мишенью и бросить")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP31.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -4))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Прицелиться слегка перед мишенью и бросить")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP30.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Прицелиться точно в мишень и бросить")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP29.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -4))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -7))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP22 = eventService.create(
                EventStore.builder()
                        .descr("Ритмичными движениями рук вы стали спускаться на дно. Это требовало некоторой нагрузки, и вы немного устали.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL15.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP21 = eventService.create(
                EventStore.builder()
                        .descr("Вы избавились от лишнего кислорода и стали медленно опускаться вниз. Вы неплохо отдохнули, но от напряжения, чтобы не дышать, сердце заработало учащенно.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL15.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL5 = eventService.create(
                EventStore.builder()
                        .descr("Теперь, как следует из логики, нужно опуститься обратно на дно.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Подгребать руками вверх")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP22.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Выпустить воздух и расслабиться")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP21.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP25 = eventService.create(
                EventStore.builder()
                        .descr("У вас хорошо получилось, вы поднялись метра на два и зависли посередине бассейна.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL5.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL12 = eventService.create(
                EventStore.builder()
                        .descr("Хорошо, но как-то все-таки надо подняться вверх, или вы хотите остаться без денег?")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Набрать побольше воздуха, расслабиться и оттолкнуться ногами от пола")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP25.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP24 = eventService.create(
                EventStore.builder()
                        .descr("У вас хорошо получилось, вы поднялись метра на два и зависли посередине бассейна.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL5.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP23 = eventService.create(
                EventStore.builder()
                        .descr("После такого неудачного упражнения нужно привести в порядок дыхание и пульс.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL12.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL10 = eventService.create(
                EventStore.builder()
                        .descr("Уф, это оказалось трудней, чем вы предполагали.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Остаться стоять на дне и немного отдохнуть")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP23.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 2))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Набрать побольше воздуха, расслабиться и оттолкнуться ногами от пола")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP24.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP19 = eventService.create(
                EventStore.builder()
                        .descr("У вас хорошо получилось, вы поднялись метра на два и зависли посередине бассейна.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL5.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP20 = eventService.create(
                EventStore.builder()
                        .descr("Вы принялись бултыхаться, но не смогли подняться выше, чем на полметра. Вы устали и разозлились.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL10.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL8 = eventService.create(
                EventStore.builder()
                        .descr("ИСПЫТУЕМЫЙ, ДВИГАЙТЕСЬ ВВЕРХ И ВНИЗ.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Грести ногами и руками вверх")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP20.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -9))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -5))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Набрать побольше воздуха, расслабиться и оттолкнуться ногами от пола")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP19.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -1))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 3))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP11 = eventService.create(
                EventStore.builder()
                        .descr("Что тут поделать, кто платит, тот и заказывает музыку. Вы стали медленно ускоряться. Вы бегаете какое-то время, пока не поступает сигнала от оператора.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL8.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP12 = eventService.create(
                EventStore.builder()
                        .descr("Что тут поделать, кто платит, тот и заказывает музыку. Вы постарались увеличить скорость бега; от смены режима вы ощутили, как у вас участилось сердцебиение. Сразу же поступил сигнал от оператора.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL8.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL4 = eventService.create(
                EventStore.builder()
                        .descr("ИСПЫТУЕМЫЙ, ВЫ БЕГАЕТЕ СЛИШКОМ МЕДЛЕННО. УВЕЛИЧЬТЕ СКОРОСТЬ.\n\nТьфу ты, стараешься для них, а они еще и ругаются!")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Резко увеличить скорость")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP12.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -7))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Плавно увеличить скорость")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP11.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -3))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -3))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP10 = eventService.create(
                EventStore.builder()
                        .descr("Вы побежали, насколько это позволяла консистенция среды, в которой вы находились. Через некоторое время поступил сигнал от оператора.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL8.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP9 = eventService.create(
                EventStore.builder()
                        .descr("Что ж, начнем, подумали вы и неторопясь побежали... насколько это слово применимо к таким непростым условиям, как пребывание на дне большого стакана сиропа.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL4.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -6))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreL3 = eventService.create(
                EventStore.builder()
                        .descr("ТЕСТ №1: ЗАМЕРЫ МЫШЕЧНЫХ ХАРАКТЕРИСТИК. ИСПЫТУЕМЫЙ, НАЧИНАЙТЕ БЕГАТЬ ПО ПЕРИМЕТРУ БАССЕЙНА.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Бегать медленно")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP9.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -5))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -3))
                                                                        .build()
                                                        ))
                                                        .build()))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Бегать в нормальном темпе")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP10.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -5))
                                                                        .build()
                                                        ))
                                                        .build()))
                                        .build()
                        ))
                        .build());

        EventStore eventStoreP6 = eventService.create(
                EventStore.builder()
                        .descr("Вы заняли свою голову тем, чтобы подумать, как лучше всего действовать, чтобы принести максимальную пользу пеленгской науке. Но поскольку данная область была вам абсолютно незнакомой, вы поняли только то, что ничего не поняли, и от этого только расстроились. И на кой черт вам вообще дался прогресс пеленгской науки?")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL3.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build()
        );
        EventStore eventStoreP5 = eventService.create(
                EventStore.builder()
                        .descr("Вы окунулись в приятные воспоминания детства, вспомнили родных, друзей, маму, играющую с вами в куклы... то есть, не с вами, конечно, а с вашей младшей сестрой. Настроение у вас улучшилось, и жизнь стала казаться не такой уж мрачной штукой.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL3.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build()
        );

        EventStore eventStoreP3 = eventService.create(
                EventStore.builder()
                        .descr("Вы предприняли все мыслимые усилия, чтобы изгнать из своей головы еще оставшиеся там мысли. В итоге вы поняли, что ничего толком не поняли.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL3.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build()
        );

        EventStore eventStoreL2 = eventService.create(
                EventStore.builder()
                        .descr("Вам еще нужно подумать о том, чем занять голову.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Думать о предстоящем ответственном эксперименте и вашей роли в нем")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP6.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -8))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Думать о доме, о семье, о зеленой лужайке за окном")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP5.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", 10))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Постараться ни о чем не думать")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreP3.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()

                        ))
                        .build()
        );

        EventStore eventStore7 = eventService.create(
                EventStore.builder()
                        .descr("Вы вспомнили, как в детском саду вас учили делать утреннюю гимнастику, и несколько раз медленно подняли и опустили руки. Это благотворно сказалось на работе сердца.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL2.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build()
        );

        EventStore eventStore4 = eventService.create(
                EventStore.builder()
                        .descr("Вы принялись бегать по дну бассейна, насколько это позволяла вязкая жидкость. Через некоторое время вы ощутили, что подустали, зато приобрели определенный опыт, как нужно правильно двигаться в такой среде.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL2.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build()
        );

        EventStore eventStore2 = eventService.create(
                EventStore.builder()
                        .descr("Вы расслабились и заняли прямое положение. Усталость несколько прошла, но сердце все еще работало учащенно.")
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Далее")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStoreL2.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .build()
        );

        EventStore eventStore1 = eventService.create(
                EventStore.builder()
                        .descr("""
                                - Замечательно! Очень хорошо. Пройдемте.
                                                                
                                На вас надели акваланг и эластичный закрытый скафандр-маску, прицепили несколько датчиков к разным частям тела, и на подъемнике погрузили в бассейн на глубину около пяти метров.
                                                                
                                Жидкость вокруг вас густая, как сироп, темная, создает несколько неприятное ощущение на коже. В процессе тяжелого погружения у вас участилось сердцебиение, кроме того, вы несколько подустали, пока поправляли маску - в тяжелой жидкости все движения требуют значительных усилий. Вы немного разнервничались и два раза выругались матом.
                                                                
                                Наконец вы достигли дна и стали на обе ноги. Холодно и страшно. Вы поймали себя на мысли о том, что стали жалеть, что согласились на эту экзекуцию. А вдруг вас обманывают, и никто и не будет стараться вас отсюда достать?
                                                                
                                В наушниках вы услышали голос оператора:
                                СТАДИЯ ПОДГОТОВКИ. ИСПЫТУЕМЫЙ, ПОДГОТОВЬТЕСЬ К ТЕСТАМ.
                                """)
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Делать простые упражнения - руки вверх, руки вниз")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore7.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 10))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Бегать легкой трусцой")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore4.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -8))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -2))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", 10))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Стоять неподвижно")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore2.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", 10))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", 1))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(false)
                        .build()
        );

        EventStore eventStore34 = eventService.create(
                EventStore.builder()
                        .descr("""
                                - Ну как хотите, мы вас не заставляем.
                                                                
                                Вы покинули это неприятное место и вернулись в космопорт.
                                """)
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Завершить задание")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.FINISH_QUEST)
                                                                        .params(new StartEventParam())
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(true)
                        .build());

        EventStore eventStore39 = eventService.create(
                EventStore.builder()
                        .descr("""
                                - В каких условиях, вы сказали? Полуподпольных? - Гришхилл засмеялся пищащим пеленгским смехом. - С чего это вы взяли? Помещение это мы нашли с большим трудом, эти гаальцы сами знаете какие цены заламывают, вот и пришлось купить подвал. Нам сверх прочего приходится еще и на энергии экономить, потому-то здесь так тускло. А на гаальской планете потому, что мы здесь работаем, в их благотворительных проектах. Самостоятельными исследованиями занимаемся по совместительству.
                                """)
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Хорошо, я все понял, давайте начнем")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -11))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -17))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -15))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -13))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -9))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Нет уж, найдите себе другого подопытного кролика")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore34.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(true)
                        .build());

        EventStore eventStore32 = eventService.create(
                EventStore.builder()
                        .descr("""
                                - Очень ценю людей, любящих быть в курсе всего, - Гришхилл похлопал вас по плечу. - Видите ли, эмм, мы занимаемся изучением того, как разные химические вещества влияют на человеческий и другие организмы. Это нужно для того, чтобы изобретать новые лекарства, вырабатывать более эффективные виды синтетической пищи, ну, вы понимаете. Мы исследуем мышечные реакции, работу сердца и мозга, других органов, в разных химических и биологических условиях.
                                - И каковы будут условия сейчас? - спросили вы.
                                - Низкоконцентрированная калольная кислота. Абсолютно никакой опасности, уверяю вас.
                                """)
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("А почему вы занимаетесь этим на гаальской планете и в таких, ммм, полуподпольных условиях?")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(List.of(
                                                ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore39.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                        ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Нет уж, найдите себе другого подопытного кролика")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore34.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Хорошо, я все понял, давайте начнем")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -11))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -17))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -15))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -13))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -9))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(true)
                        .build());

        EventStore eventStore40 = eventService.create(
                EventStore.builder()
                        .descr("""
                                - Очень ценю людей, любящих быть в курсе всего, - Гришхилл похлопал вас по плечу. - Видите ли, эмм, мы занимаемся изучением того, как разные химические вещества влияют на человеческий и другие организмы. Это нужно для того, чтобы изобретать новые лекарства, вырабатывать более эффективные виды синтетической пищи, ну, вы понимаете. Мы исследуем мышечные реакции, работу сердца и мозга, других органов, в разных химических и биологических условиях.
                                - И каковы будут условия сейчас? - спросили вы.
                                - Низкоконцентрированная калольная кислота. Абсолютно никакой опасности, уверяю вас.
                                """)
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Хорошо, я все понял, давайте начнем")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -11))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -17))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -15))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -13))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -9))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Нет уж, найдите себе другого подопытного кролика")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore34.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()

                        ))
                        .hideState(true)
                        .build());

        EventStore eventStore33 = eventService.create(
                EventStore.builder()
                        .descr("""
                                - В каких условиях, вы сказали? Полуподпольных? - Гришхилл засмеялся пищащим пеленгским смехом. - С чего это вы взяли? Помещение это мы нашли с большим трудом, эти гаальцы сами знаете какие цены заламывают, вот и пришлось купить подвал. Нам сверх прочего приходится еще и на энергии экономить, потому-то здесь так тускло. А на гаальской планете потому, что мы здесь работаем, в их благотворительных проектах. Самостоятельными исследованиями занимаемся по совместительству.
                                """)
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Хотелось бы знать более конкретно, что именно вы изучаете")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore40.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Нет уж, найдите себе другого подопытного кролика")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore34.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("Хорошо, я все понял, давайте начнем")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Физическое состояние", -11))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Психологическое состояние", -17))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Работа сердца", -15))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Координация", -13))
                                                                        .build(),
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.CHANGE_NUM_STATE)
                                                                        .params(new ChangeNumStateParam("Самочувствие", -9))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
                        .hideState(true)
                        .build());

        EventStore eventStore31 = eventService.create(
                EventStore.builder()
                        .descr("""
                                Лаборатория, в которую вы прибыли, находилась почему-то под землей и вообще представляла собой некое подобие конспиративной квартиры. За вами тщательно закрыли дверь, предварительно удостоверившись, не видел ли кто вашего прихода. Вообще, вам все здесь показалось подозрительным.
                                Вас встретил пеленг в желтом халате и перчатках на трех руках. Свободной рукой он пожал вашу руку, по традиции людей. Представившись как Гришхилл, руководитель эксперимента, он проводил вас в свой кабинет.
                                                                
                                - Итак, {ranger_name}, думаю, вам понятна ваша роль, - закончил он свою речь. - Погружение в бассейн, несложные операции под водой. Акваланг, маска, несколько датчиков для замеров данных - все, что будет на вас. Вы будете получать от оператора простые инструкции, и следовать им. Когда все тесты закончатся, вы подниметесь на поверхность. Опасности никакой нет, все внештатные ситуации строго под контролем. К тому же, если вам станет плохо, вы можете сами воспользоваться подъемником и подняться наверх. Только в этом случае вы, конечно же, не получите никакого гонорара. Для того чтобы его получить, нужно пройти весь цикл тестов.
                                """
                        )
                        .actions(List.of(
                                ActionStore.builder()
                                        .descr("Хотелось бы знать более конкретно, что именно вы изучаете")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(
                                                List.of(ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore32.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build(),
                                ActionStore.builder()
                                        .descr("А почему вы занимаетесь этим на гаальской планете и в таких, ммм, полуподпольных условиях?")
                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                        .hideImprobable(true)
                                        .results(List.of(
                                                ResultStore.builder()
                                                        .condition(new SingleExpression(alwaysTrueCondition.getId()))
                                                        .consequences(List.of(
                                                                ConsequenceStore.builder()
                                                                        .type(ConsequenceType.NEXT_EVENT)
                                                                        .params(new StartEventParam(eventStore33.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                        ))
                                        .build()
                        ))
                        .hideState(true)
                        .build());
        questService.updateEvents(quest, List.of(eventStore31, eventStore33, eventStore32));
    }
}
