package dev.blynchik.quest_bot.service.model.content;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.result.ResultStore;
import dev.blynchik.quest_bot.repo.content.ActionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ActionService {
    private final ActionRepo actionRepo;
    private final ResultService resultService;

    @Autowired
    public ActionService(ActionRepo actionRepo,
                         ResultService resultService) {
        this.actionRepo = actionRepo;
        this.resultService = resultService;
    }

    public Optional<ActionStore> getByIdOpt(Long id) {
        log.info("Get optional action by id: {}", id);
        return actionRepo.findById(id);
    }

    public ActionStore getById(Long id) {
        log.info("Get action by id: {}", id);
        return actionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Действие не найдено."));
    }

    public List<ResultStore> getResultsByActionId(Long id) {
        log.info("Get action's id: {} results", id);
        return actionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Действие не найдено."))
                .getResults().stream()
                .map(r -> resultService.getById(r.getId()))
                .toList();
    }
}
