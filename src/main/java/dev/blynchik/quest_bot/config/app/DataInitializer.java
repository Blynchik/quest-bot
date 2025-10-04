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

    @Autowired
    public DataInitializer(QuestService questService,
                           ConditionService conditionService) {
        this.questService = questService;
        this.conditionService = conditionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConditionStore alwaysTrueCondition = conditionService.create(new ConditionStore());
        ConditionStore alwaysFalseCondition = conditionService.create(new ConditionStore(new AlwaysParams(false)));

        QuestStore quest = QuestStore.builder()
                .title("Страшная смерть")
                .descr("""
                         Послушайте, {ranger_name}, окажите нам услугу. На планете {planet_name}, в системе {system_name},
                          мы проводим эксперимент по изучению воздействия разных химических веществ на человеческий
                          организм. Все, что нам нужно, это здоровый человек для эксперимента.
                          Мы платим огромные деньги - {reward_value} cr!
                          Не упустите такую возможность. Но только получить эти деньги вы сможете, если эксперимент будет
                          проведен до {date}, не иначе.
                        """)
                .events(List.of(
                        EventStore.builder()
                                .descr(
                                        """
                                                Лаборатория, в которую вы прибыли, находилась почему-то под землей и вообще представляла\s
                                                собой некое подобие конспиративной квартиры.\s
                                                За вами тщательно закрыли дверь, предварительно удостоверившись, не видел ли кто вашего\s
                                                прихода.\s
                                                Вообще, вам все здесь показалось подозрительным.\s
                                                Вас встретил пеленг в желтом халате и перчатках на трех руках.\s
                                                Свободной рукой он пожал вашу руку, по традиции людей.\s
                                                Представившись как Гришхилл, руководитель эксперимента, он проводил вас в свой кабинет.

                                                - Итак, {ranger_name}, думаю, вам понятна ваша роль, - закончил он свою речь.\s
                                                - Погружение в бассейн, несложные операции под водой.\s
                                                Акваланг, маска, несколько датчиков для замеров данных - все, что будет на вас.\s
                                                Вы будете получать от оператора простые инструкции, и следовать им.\s
                                                Когда все тесты закончатся, вы подниметесь на поверхность.\s
                                                Опасности никакой нет, все внештатные ситуации строго под контролем.\s
                                                К тому же, если вам станет плохо, вы можете сами воспользоваться подъемником и\s
                                                подняться наверх.\s
                                                Только в этом случае вы, конечно же, не получите никакого гонорара.\s
                                                Для того чтобы его получить, нужно пройти весь цикл тестов.
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
                                                                                .params(new StartEventParam())
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
                                                                                .params(new StartEventParam())
                                                                                .build()
                                                                ))
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build()
                ))
                .build();
        quest.setFirstEvent(quest.getEvents().get(0));
        questService.create(quest);
    }
}
