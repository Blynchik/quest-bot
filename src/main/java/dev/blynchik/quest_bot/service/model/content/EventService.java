package dev.blynchik.quest_bot.service.model.content;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.repo.content.EventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class EventService {
    private final EventRepo eventRepo;
    private final ActionService actionService;

    @Autowired
    public EventService(EventRepo eventRepo, ActionService actionService) {
        this.eventRepo = eventRepo;
        this.actionService = actionService;
    }

    public List<ActionStore> getActionsByEventId(Long id) {
        log.info("Get actions for event id: {}", id);
        return eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"))
                .getActions().stream()
                .map(a -> actionService.getById(a.getId()))
                .toList();
    }

    public Optional<EventStore> getByIdOpt(Long id) {
        log.info("Get optional event id: {}", id);
        return eventRepo.findById(id);
    }

    public EventStore getById(Long id) {
        log.info("Get event by id: {}", id);
        return eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));
    }
}
