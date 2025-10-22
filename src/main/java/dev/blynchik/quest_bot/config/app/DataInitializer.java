package dev.blynchik.quest_bot.config.app;

import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.ChangeNumStateParam;
import dev.blynchik.quest_bot.model.content.consequence.params.impl.StartEventParam;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionStore;
import dev.blynchik.quest_bot.model.content.expression.condition.params.impl.AlwaysParams;
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
                                                                        .params(new StartEventParam())
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
