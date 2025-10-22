package dev.blynchik.quest_bot.service.model.quest;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import dev.blynchik.quest_bot.repo.content.QuestRepo;
import dev.blynchik.quest_bot.service.model.content.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class QuestService {
    private final QuestRepo questRepo;
    private final EventService eventService;

    @Autowired
    public QuestService(QuestRepo questRepo, EventService eventService) {
        this.questRepo = questRepo;
        this.eventService = eventService;
    }

    public QuestStore getRandom() {
        log.info("Get random quest");
        return questRepo.findRandom()
                .orElseThrow(() -> new NotFoundException("Ни одного квеста не найдено."));
    }

    public QuestStore getById(Long id) {
        log.info("Get quest by id: {}", id);
        return questRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Квест не найден"));
    }

    public Optional<QuestStore> getByIdOpt(Long id) {
        log.info("Get optional quest by id: {}", id);
        return questRepo.findById(id);
    }

    public EventStore getFirstEventByQuestId(Long id) {
        log.info("Get first event for quest id: {}", id);
        return eventService.getById(
                questRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException("Квест не найден"))
                        .getFirstEvent().getId());
    }

    @Transactional
    public QuestStore create(QuestStore quest) {
        log.info("Create quest: {}", quest);
        if (quest.getId() != null)
            throw new IllegalArgumentException("При создании квеста используется уже созданный квест id: %s".formatted(quest.getId()));
        return questRepo.save(quest);
    }

    @Transactional
    public QuestStore updateEvents(QuestStore quest, List<EventStore> events) {
        log.info("Create quest: {}", quest);
        if (quest.getId() == null)
            throw new IllegalArgumentException("При обновлении квеста используется несуществующий квест");
        quest.setFirstEvent(events.get(0));
        quest.setEvents(events);
        return questRepo.save(quest);
    }
}
