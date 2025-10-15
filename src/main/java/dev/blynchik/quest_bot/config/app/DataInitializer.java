package dev.blynchik.quest_bot.config.app;

import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceType;
import dev.blynchik.quest_bot.model.content.consequence.params.StartEventParam;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.content.expression.SingleExpression;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionStore;
import dev.blynchik.quest_bot.model.content.expression.condition.params.AlwaysParams;
import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import dev.blynchik.quest_bot.model.content.result.ResultStore;
import dev.blynchik.quest_bot.service.model.content.ConditionService;
import dev.blynchik.quest_bot.service.model.content.EventService;
import dev.blynchik.quest_bot.service.model.content.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

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

        QuestStore quest = questService.create(
                QuestStore.builder()
                        .title("Страшная смерть")
                        .descr("""
                                Послушайте, {ranger_name}, окажите нам услугу. На планете {planet_name}, в системе {system_name}, мы проводим эксперимент по изучению воздействия разных химических веществ на человеческий организм. Все, что нам нужно, это здоровый человек для эксперимента. Мы платим огромные деньги - {reward_value} cr! Не упустите такую возможность. Но только получить эти деньги вы сможете, если эксперимент будет проведен до {date}, не иначе.
                                """)
                        .build());

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
                                                                        .type(ConsequenceType.FINISH_EVENT)
                                                                        .params(new StartEventParam())
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
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
                                                                        .type(ConsequenceType.START_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
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
                                                                        .type(ConsequenceType.START_EVENT)
                                                                        .params(new StartEventParam(eventStore34.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
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
                                                                        .type(ConsequenceType.START_EVENT)
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
                                                                        .type(ConsequenceType.START_EVENT)
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
                                                                        .type(ConsequenceType.START_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
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
                                                                        .type(ConsequenceType.START_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
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
                                                                        .type(ConsequenceType.START_EVENT)
                                                                        .params(new StartEventParam(eventStore34.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()

                        ))
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
                                                                        .type(ConsequenceType.START_EVENT)
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
                                                                        .type(ConsequenceType.START_EVENT)
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
                                                                        .type(ConsequenceType.START_EVENT)
                                                                        .params(new StartEventParam(eventStore1.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                                ))
                                        .build()
                        ))
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
                                                                        .type(ConsequenceType.START_EVENT)
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
                                                                        .type(ConsequenceType.START_EVENT)
                                                                        .params(new StartEventParam(eventStore33.getId()))
                                                                        .build()
                                                        ))
                                                        .build()
                                        ))
                                        .build()
                        ))
                        .build());
        questService.updateEvents(quest, List.of(eventStore31, eventStore33, eventStore32));
    }
}
