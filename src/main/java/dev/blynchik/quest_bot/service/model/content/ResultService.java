package dev.blynchik.quest_bot.service.model.content;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.model.content.result.ResultStore;
import dev.blynchik.quest_bot.repo.content.ResultRepo;
import dev.blynchik.quest_bot.service.model.content.consequence.ConsequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ResultService {
    private final ResultRepo resultRepo;
    private final ConsequenceService consequenceService;

    public ResultService(ResultRepo resultRepo,
                         ConsequenceService consequenceService) {
        this.resultRepo = resultRepo;
        this.consequenceService = consequenceService;
    }

    public ResultStore getById(Long id) {
        log.info("Get result by id: {}", id);
        return resultRepo.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Результат не найден."));
    }

    public List<ConsequenceStore> getConsequencesByResultId(Long id) {
        log.info("Get result's id: {} consequences", id);
        return resultRepo.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Результат не найден"))
                .getConsequences().stream()
                .map(c -> consequenceService.getById(c.getId()))
                .toList();
    }
}
